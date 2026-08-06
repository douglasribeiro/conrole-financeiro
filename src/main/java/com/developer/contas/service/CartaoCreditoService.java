package com.developer.contas.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.contas.dto.CartaoCreditoDTO;
import com.developer.contas.entity.secundario.CartaoCredito;
import com.developer.contas.entity.secundario.LancamentoFinanceiro;
import com.developer.contas.entity.secundario.Parceiro;
import com.developer.contas.entity.secundario.PlanoContas;
import com.developer.contas.entity.secundario.TipoParceiro;
import com.developer.contas.entity.secundario.TransacoesCartao;
import com.developer.contas.enuns.StatusLancamento;
import com.developer.contas.enuns.TipoLancamento;
import com.developer.contas.exception.ResourceNotFoundException;
import com.developer.contas.generics.BaseService;
import com.developer.contas.repository.secundario.CartaoCreditoRepository;
import com.developer.contas.repository.secundario.LancamentoFinanceiroRepository;
import com.developer.contas.repository.secundario.ParceiroRepository;
import com.developer.contas.repository.secundario.PlanoContasRepository;
import com.developer.contas.repository.secundario.TransacoesCartaoRepository;

@Service
public class CartaoCreditoService extends BaseService<CartaoCredito, CartaoCreditoDTO, Long> {

	private static final Logger log = LoggerFactory.getLogger(CartaoCreditoService.class);
	
	private final CartaoCreditoRepository cartaoRepository;
    private final TransacoesCartaoRepository transacaoRepository;
    private final LancamentoFinanceiroRepository financeiroRepository;
    private final ParceiroRepository parceiroRepository;
    private final PlanoContasRepository planoContasRepository;

    public CartaoCreditoService(
            CartaoCreditoRepository cartaoRepository,
            TransacoesCartaoRepository transacaoRepository,
            LancamentoFinanceiroRepository financeiroRepository,
            ParceiroRepository parceiroRepository,
            PlanoContasRepository planoContasRepository) {
        
        super(cartaoRepository, CartaoCredito.class, CartaoCreditoDTO.class);
        
        this.cartaoRepository = cartaoRepository;
        this.transacaoRepository = transacaoRepository;
        this.financeiroRepository = financeiroRepository;
        this.parceiroRepository = parceiroRepository;
        this.planoContasRepository = planoContasRepository;
    }


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

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void fecharFaturasAgendadas() {
    	log.info("Inicio do processo de fechar faturas são {}", LocalTime.now());
        LocalDate hoje = LocalDate.now();
        int diaAtual = hoje.getDayOfMonth();

        List<CartaoCredito> cartoesParaFechar = cartaoRepository.findByDiaFechamento(diaAtual);
        String mesAnoFatura = hoje.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        for (CartaoCredito cartao : cartoesParaFechar) {
            List<TransacoesCartao> transacoes = transacaoRepository
                    .findByCartaoCreditoIdAndFaturaMesAno(cartao.getId(), mesAnoFatura);

            if (transacoes.isEmpty()) {
                continue; 
            }

            BigDecimal totalFatura = transacoes.stream()
                    .map(TransacoesCartao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            cartao.setLimiteDisponivel(cartao.getLimiteDisponivel().add(totalFatura));
            cartaoRepository.save(cartao);

            // ====================================================================
            // CORREÇÃO: LOCALIZAR PARCEIRO E PLANO DE CONTAS OBRIGATÓRIOS
            // ====================================================================
            // Regra de negócio: Você pode buscar o fornecedor correto ou associar de forma dinâmica
            // Aqui buscamos o primeiro fornecedor ativo ou o específico da operadora do teste
            Parceiro operadoraCartao = parceiroRepository.findAll().stream()
                    .filter(p -> p.getTipo() == TipoParceiro.FORNECEDOR)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Nenhum parceiro Fornecedor/Operadora cadastrado."));

            PlanoContas contaContabilFatura = planoContasRepository.findByCodigoEstrutural("2.01.01.002")
                    .orElseThrow(() -> new IllegalStateException("Plano de contas de fatura não encontrado."));

            // GERAR CONTAS A PAGAR AUTOMÁTICO
            LancamentoFinanceiro contaAPagar = new LancamentoFinanceiro();
            contaAPagar.setEmpresa(cartao.getContaBancaria().getEmpresa());
            
            // VÍNCULOS CORRIGIDOS AQUI 
            contaAPagar.setParceiro(operadoraCartao);      // Resolve o erro 'parceiro_id' cannot be null
            contaAPagar.setPlanoContas(contaContabilFatura); // Resolve o erro 'plano_contas_id' futuro
            
            contaAPagar.setDescricao("Fatura Cartão: " + cartao.getNomeCartao() + " - Ref: " + mesAnoFatura);
            contaAPagar.setTipoLancamento(TipoLancamento.PAGAR);
            contaAPagar.setValorOriginal(totalFatura);
            contaAPagar.setValorPago(BigDecimal.ZERO);
            contaAPagar.setDataEmissao(hoje);
            
            LocalDate vencimento = hoje.withDayOfMonth(cartao.getDiaVencimento());
            if (cartao.getDiaVencimento() < cartao.getDiaFechamento()) {
                vencimento = vencimento.plusMonths(1);
            }
            contaAPagar.setDataVencimento(vencimento);
            contaAPagar.setStatus(StatusLancamento.ABERTO);

            // Linha 120 que estava estourando a exceção:
            financeiroRepository.save(contaAPagar); 

            for (TransacoesCartao t : transacoes) {
                t.setLancamentoFinanceiro(contaAPagar);
                transacaoRepository.save(t);
            }
        }
    }
}