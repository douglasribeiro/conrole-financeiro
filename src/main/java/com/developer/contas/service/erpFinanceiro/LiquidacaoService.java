package com.developer.contas.service.erpFinanceiro;

import com.developer.contas.entity.erpFinanceiro.*;
import com.developer.contas.entity.erpFinanceiro.enuns.*;
import com.developer.contas.repository.erpFinanceiro.ContaBancariaRepository;
import com.developer.contas.repository.erpFinanceiro.LancamentoFinanceiroRepository;
import com.developer.contas.repository.erpFinanceiro.LoteContabilRepository;
import com.developer.contas.repository.erpFinanceiro.MovimentacaoBancariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LiquidacaoService {

    private final LancamentoFinanceiroRepository financeiroRepository;
    private final MovimentacaoBancariaRepository movimentacaoRepository;
    private final ContaBancariaRepository contaBancariaRepository;
    private final LoteContabilRepository loteContabilRepository;

    @Transactional
    public void liquidarFatura(Long lancamentoFinanceiroId, Long contaBancariaId, BigDecimal valorPago) {

        // 1. VALIDAÇÕES INICIAIS
        LancamentoFinanceiro titulo = financeiroRepository.findById(lancamentoFinanceiroId)
                .orElseThrow(() -> new IllegalArgumentException("Lançamento financeiro não encontrado."));

        ContaBancaria conta = contaBancariaRepository.findById(contaBancariaId)
                .orElseThrow(() -> new IllegalArgumentException("Conta bancária não encontrada."));

        if (titulo.getStatus() == StatusLancamento.LIQUIDADO) {
            throw new IllegalStateException("Este título já foi liquidado.");
        }

        // 2. ATUALIZAR TÍTULO FINANCEIRO (CONTAS A PAGAR / RECEBER)
        titulo.setValorPago(titulo.getValorPago().add(valorPago));
        if (titulo.getValorPago().compareTo(titulo.getValorOriginal()) >= 0) {
            titulo.setStatus(StatusLancamento.LIQUIDADO);
        } else {
            titulo.setStatus(StatusLancamento.PARCIAL);
        }
        financeiroRepository.save(titulo);

        // 3. ATUALIZAR SALDO BANCÁRIO E GERAR MOVIMENTAÇÃO (EXTRATO)
        TipoMovimentacao tipoMov = (titulo.getTipoLancamento() == TipoLancamento.PAGAR)
                ? TipoMovimentacao.SAIDA
                : TipoMovimentacao.ENTRADA;

        if (tipoMov == TipoMovimentacao.SAIDA) {
            if (conta.getSaldoAtual().compareTo(valorPago) < 0) {
                throw new IllegalStateException("Saldo insuficiente na conta bancária para efetuar o pagamento.");
            }
            conta.setSaldoAtual(conta.getSaldoAtual().subtract(valorPago));
        } else {
            conta.setSaldoAtual(conta.getSaldoAtual().add(valorPago));
        }
        contaBancariaRepository.save(conta);

        MovimentacaoBancaria movBancaria = new MovimentacaoBancaria();
        movBancaria.setContaBancaria(conta);
        movBancaria.setLancamentoFinanceiro(titulo);
        movBancaria.setTipoMovimentacao(tipoMov);
        movBancaria.setValor(valorPago);
        movBancaria.setDataMovimentacao(LocalDateTime.now());
        movBancaria.setHistorico("Liquidação do doc nº: " + titulo.getNumeroDocumento() + " - " + titulo.getDescricao());
        movimentacaoRepository.save(movBancaria);

        // 4. GERAÇÃO AUTOMÁTICA DA CONTABILIDADE (PARTIDAS DOBRADAS)
        gerarContabilidadeAutomatica(titulo, conta, valorPago);
    }

    private void gerarContabilidadeAutomatica(LancamentoFinanceiro titulo, ContaBancaria conta, BigDecimal valor) {
        LoteContabil lote = new LoteContabil();
        lote.setDataFato(LocalDate.now());
        lote.setOrigem(OrigemLote.FINANCEIRO);
        lote.setDescricao("Integração Automática - Liquidação Título ID: " + titulo.getId());

        LancamentoContabil linhaDebito = new LancamentoContabil();
        LancamentoContabil linhaCredito = new LancamentoContabil();

        if (titulo.getTipoLancamento() == TipoLancamento.PAGAR) {
            // Cenário Contas a Pagar:
            // DÉBITO: Conta de Passivo (Fornecedores / Obrigações) -> Reduz a dívida
            linhaDebito.setPlanoContas(titulo.getPlanoContas());
            linhaDebito.setTipoLinha(TipoLinhaContabil.DEBITO);
            linhaDebito.setValor(valor);

            // CRÉDITO: Conta de Ativo (Disponibilidades / Banco) -> Reduz o dinheiro no banco
            // *Nota: Em um cenário real, você deve buscar o PlanoContas vinculado à ContaBancaria ativa*
            linhaCredito.setPlanoContas(titulo.getPlanoContas()); // Substituir pelo plano_contas_id da Conta Bancária
            linhaCredito.setTipoLinha(TipoLinhaContabil.CREDITO);
            linhaCredito.setValor(valor);
        } else {
            // Cenário Contas a Receber:
            // DÉBITO: Conta de Ativo (Disponibilidades / Banco) -> Aumenta o dinheiro no banco
            linhaDebito.setPlanoContas(titulo.getPlanoContas()); // Substituir pelo plano_contas_id da Conta Bancária
            linhaDebito.setTipoLinha(TipoLinhaContabil.DEBITO);
            linhaDebito.setValor(valor);

            // CRÉDITO: Conta de Ativo (Clientes a Receber) -> Reduz o direito de receber do cliente
            linhaCredito.setPlanoContas(titulo.getPlanoContas());
            linhaCredito.setTipoLinha(TipoLinhaContabil.CREDITO);
            linhaCredito.setValor(valor);
        }

        // Validação estrita de partidas dobradas antes de salvar
        if (linhaDebito.getValor().compareTo(linhaCredito.getValor()) != 0) {
            throw new IllegalStateException("Erro Crítico Contábil: Total de Débitos difere do Total de Créditos.");
        }

        lote.adicionarLinha(linhaDebito);
        lote.adicionarLinha(linhaCredito);

        loteContabilRepository.save(lote);
    }
}
