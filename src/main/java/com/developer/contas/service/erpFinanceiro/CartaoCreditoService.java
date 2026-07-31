package com.developer.contas.service.erpFinanceiro;

import com.developer.contas.entity.erpFinanceiro.CartaoCredito;
import com.developer.contas.entity.erpFinanceiro.LancamentoFinanceiro;
import com.developer.contas.entity.erpFinanceiro.TransacoesCartao;
import com.developer.contas.entity.erpFinanceiro.enuns.StatusLancamento;
import com.developer.contas.entity.erpFinanceiro.enuns.TipoLancamento;
import com.developer.contas.exception.erpFinanceiro.ResourceNotFoundException;
import com.developer.contas.repository.erpFinanceiro.CartaoCreditoRepository;
import com.developer.contas.repository.erpFinanceiro.LancamentoFinanceiroRepository;
import com.developer.contas.repository.erpFinanceiro.TransacoesCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoCreditoService {

    private final CartaoCreditoRepository cartaoRepository;
    private final TransacoesCartaoRepository transacaoRepository;
    private final LancamentoFinanceiroRepository financeiroRepository;

    /**
     * 1. REGISTAR NOVA COMPRA NO CARTÃO
     */
    @Transactional
    public void registrarCompra(Long cartaoId, String estabelecimento, BigDecimal valor, Integer parcelas) {
        CartaoCredito cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão de crédito não encontrado."));

        if (cartao.getLimiteDisponivel().compareTo(valor) < 0) {
            throw new IllegalStateException("Limite insuficiente no cartão de crédito.");
        }

        // Deduz o valor total do limite disponível do cartão
        cartao.setLimiteDisponivel(cartao.getLimiteDisponivel().subtract(valor));
        cartaoRepository.save(cartao);

        // Define o mês de competência da fatura atual (Ex: "2026-08")
        LocalDate hoje = LocalDate.now();
        BigDecimal valorParcela = valor.divide(BigDecimal.valueOf(parcelas), 2, BigDecimal.ROUND_HALF_UP);

        for (int i = 1; i <= parcelas; i++) {
            TransacoesCartao transacao = new TransacoesCartao();
            transacao.setCartaoCredito(cartao);
            transacao.setEstabelecimento(estabelecimento);
            transacao.setValor(valorParcela);
            transacao.setDataTransacao(LocalDateTime.now());
            transacao.setNumeroParcela(i);
            transacao.setTotalParcelas(parcelas);

            // Empurra a fatura para os meses seguintes caso seja parcelado
            LocalDate dataFatura = hoje.plusMonths(i - 1);
            transacao.setFaturaMesAno(dataFatura.format(DateTimeFormatter.ofPattern("yyyy-MM")));

            transacaoRepository.save(transacao);
        }
    }

    /**
     * 2. ROTINA AGENDADA: FECHAMENTO AUTOMÁTICO DE FATURAS
     * Executa todos os dias à meia-noite (0 0 0 * * ?)
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void fecharFaturasAgendadas() {
        LocalDate hoje = LocalDate.now();
        int diaAtual = hoje.getDayOfMonth();

        // Encontra todos os cartões que possuem o fechamento no dia de hoje
        List<CartaoCredito> cartoesParaFechar = cartaoRepository.findByDiaFechamento(diaAtual);

        String mesAnoFatura = hoje.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        for (CartaoCredito cartao : cartoesParaFechar) {
            // Busca todas as transações da fatura corrente deste cartão
            List<TransacoesCartao> transacoes = transacaoRepository
                    .findByCartaoCreditoIdAndFaturaMesAno(cartao.getId(), mesAnoFatura);

            if (transacoes.isEmpty()) {
                continue; // Próximo cartão se não houver gastos
            }

            // Soma o total gasto na fatura
            BigDecimal totalFatura = transacoes.stream()
                    .map(TransacoesCartao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Devolve o limite utilizado de volta ao cartão após o fechamento da fatura
            cartao.setLimiteDisponivel(cartao.getLimiteDisponivel().add(totalFatura));
            cartaoRepository.save(cartao);

            // GERAR CONTAS A PAGAR AUTOMÁTICO (Para a operadora do cartão)
            LancamentoFinanceiro contaAPagar = new LancamentoFinanceiro();
            contaAPagar.setEmpresa(cartao.getContaBancaria().getEmpresa());

            // Assume-se que você tenha regras para buscar o Parceiro (Operadora) e Plano de Contas correto
            contaAPagar.setDescricao("Fatura Cartão: " + cartao.getNomeCartao() + " - Ref: " + mesAnoFatura);
            contaAPagar.setTipoLancamento(TipoLancamento.PAGAR);
            contaAPagar.setValorOriginal(totalFatura);
            contaAPagar.setValorPago(BigDecimal.ZERO);
            contaAPagar.setDataEmissao(hoje);

            // Define o vencimento da fatura com base no dia cadastrado no cartão
            LocalDate vencimento = hoje.withDayOfMonth(cartao.getDiaVencimento());
            if (cartao.getDiaVencimento() < cartao.getDiaFechamento()) {
                vencimento = vencimento.plusMonths(1); // Se o vencimento for no mês seguinte
            }
            contaAPagar.setDataVencimento(vencimento);
            contaAPagar.setStatus(StatusLancamento.ABERTO);

            financeiroRepository.save(contaAPagar);

            // Vincula a fatura gerada às transações para fins de auditoria
            for (TransacoesCartao t : transacoes) {
                t.setLancamentoFinanceiro(contaAPagar);
                transacaoRepository.save(t);
            }
        }
    }
}
