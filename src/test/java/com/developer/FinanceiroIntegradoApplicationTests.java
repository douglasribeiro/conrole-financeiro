package com.developer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.developer.contas.entity.secundario.CartaoCredito;
import com.developer.contas.entity.secundario.ContaBancaria;
import com.developer.contas.entity.secundario.Empresa;
import com.developer.contas.entity.secundario.LancamentoContabil;
import com.developer.contas.entity.secundario.LancamentoFinanceiro;
import com.developer.contas.entity.secundario.LoteContabil;
import com.developer.contas.entity.secundario.Parceiro;
import com.developer.contas.entity.secundario.PlanoContas;
import com.developer.contas.enuns.NaturezaContabil;
import com.developer.contas.enuns.OrigemLote;
import com.developer.contas.enuns.StatusLancamento;
import com.developer.contas.enuns.TipoContaBancaria;
import com.developer.contas.enuns.TipoContaContabil;
import com.developer.contas.enuns.TipoLancamento;
import com.developer.contas.enuns.TipoLinhaContabil;
import com.developer.contas.repository.secundario.CartaoCreditoRepository;
import com.developer.contas.repository.secundario.ContaBancariaRepository;
import com.developer.contas.repository.secundario.EmpresaRepository;
import com.developer.contas.repository.secundario.FornecedorRepository;
import com.developer.contas.repository.secundario.LancamentoFinanceiroRepository;
import com.developer.contas.repository.secundario.LoteContabilRepository;
import com.developer.contas.repository.secundario.ParceiroRepository;
import com.developer.contas.repository.secundario.PlanoContasRepository;
import com.developer.contas.repository.secundario.TransacoesCartaoRepository;
import com.developer.contas.service.CartaoCreditoService;
import com.developer.contas.service.LiquidacaoService;


@SpringBootTest
public class FinanceiroIntegradoApplicationTests {

    @Autowired private CartaoCreditoService cartaoCreditoService;
    @Autowired private LiquidacaoService liquidacaoService;
    
    @Autowired private EmpresaRepository empresaRepository;
    @Autowired private ParceiroRepository parceiroRepository;
    @Autowired private PlanoContasRepository planoContasRepository;
    @Autowired private ContaBancariaRepository contaBancariaRepository;
    @Autowired private CartaoCreditoRepository cartaoRepository;
    @Autowired private TransacoesCartaoRepository transacaoRepository;
    @Autowired private LancamentoFinanceiroRepository financeiroRepository;
    @Autowired private LoteContabilRepository loteContabilRepository;
    @Autowired private FornecedorRepository fornecedorRepository;

    private Long cartaoId;
    private Long contaId;

    @BeforeEach
    void setUpBaseDeDados() {
        // Limpa a base de dados de teste de forma ordenada
        loteContabilRepository.deleteAll();
        transacaoRepository.deleteAll();
        financeiroRepository.deleteAll();
        cartaoRepository.deleteAll();
        contaBancariaRepository.deleteAll();

        // 1. Cadastra Empresa e Parceiro
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa Teste S/A");
        empresa.setCnpj("00000000000100");
        empresa = empresaRepository.save(empresa);

        Parceiro fornecedor = new Parceiro();
        fornecedor.setNome("Operadora Visa");
        fornecedor.setCpfCnpj("11111111000111");
        fornecedor.setTipo(com.developer.contas.entity.secundario.TipoParceiro
        		.FORNECEDOR);  ///.setTipoP(TipoParceiro.FORNECEDOR);
        fornecedorRepository.save(fornecedor);

        // 2. Cadastra Contas Contábeis Básicas
        PlanoContas contaBanco = new PlanoContas();
        contaBanco.setCodigoEstrutural("1.01.01.002");
        contaBanco.setDescricao("Banco Conta Corrente");
        contaBanco.setTipo(TipoContaContabil.ATIVO);
        contaBanco.setNatureza(NaturezaContabil.DEBITO);
        contaBanco.setNivel(4);
        planoContasRepository.save(contaBanco);

        PlanoContas contaFatura = new PlanoContas();
        contaFatura.setCodigoEstrutural("2.01.01.002");
        contaFatura.setDescricao("Faturas a Pagar");
        contaFatura.setTipo(TipoContaContabil.PASSIVO);
        contaFatura.setNatureza(NaturezaContabil.CREDITO);
        contaFatura.setNivel(4);
        planoContasRepository.save(contaFatura);

        // 3. Cadastra Conta Bancária com Saldo de R$ 5.000,00
        ContaBancaria conta = new ContaBancaria();
        conta.setEmpresa(empresa);
        conta.setBancoCodigo("001");
        conta.setNomeConta("Conta Principal");
        conta.setAgencia("1234");
        conta.setNumeroConta("5555-5");
        conta.setTipoConta(TipoContaBancaria.CORRENTE);
        conta.setSaldoAtual(BigDecimal.valueOf(5000.00));
        conta = contaBancariaRepository.save(conta);
        this.contaId = conta.getId();

        // 4. Cadastra Cartão de Crédito com Limite de R$ 2.000,00 e Fechamento HOJE
        CartaoCredito cartao = new CartaoCredito();
        cartao.setContaBancaria(conta);
        cartao.setNomeCartao("Visa Prime");
        cartao.setLimiteTotal(BigDecimal.valueOf(2000.00));
        cartao.setLimiteDisponivel(BigDecimal.valueOf(2000.00));
        cartao.setDiaFechamento(LocalDate.now().getDayOfMonth()); // Força fechar hoje
        cartao.setDiaVencimento(20);
        cartao = cartaoRepository.save(cartao);
        this.cartaoId = cartao.getId();
    }

    @Test
    @DisplayName("Deve executar o ciclo financeiro completo: Compra -> Fechamento -> Liquidação -> Contabilidade")
    @Transactional(transactionManager = "secondaryTransactionManager") 
    void deveExecutarCicloCompletoFinanceiroECodigoContabil() {
        
        // ==========================================
        // PASSO 1: Registar uma compra de R$ 300,00 à vista
        // ==========================================
        cartaoCreditoService.registrarCompra(cartaoId, "Amazon Web Services", BigDecimal.valueOf(300.00), 1);
        
        CartaoCredito cartaoPosCompra = cartaoRepository.findById(cartaoId).orElseThrow();
        assertEquals(0, BigDecimal.valueOf(1700.00).compareTo(cartaoPosCompra.getLimiteDisponivel()), 
                "O limite disponível deve cair para R$ 1700,00");

        // ==========================================
        // PASSO 2: Forçar o fechamento da fatura
        // ==========================================
        cartaoCreditoService.fecharFaturasAgendadas();

        // Verifica se o limite do cartão foi recomposto após o fechamento
        CartaoCredito cartaoPosFechamento = cartaoRepository.findById(cartaoId).orElseThrow();
        assertEquals(0, BigDecimal.valueOf(2000.00).compareTo(cartaoPosFechamento.getLimiteTotal()), 
                "O limite deve voltar a ser R$ 2000,00");

        // Procura o lançamento gerado no Contas a Pagar
        String mesAnoCorrente = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        List<LancamentoFinanceiro> contasAPagar = financeiroRepository.findAll();
        
        assertFalse(contasAPagar.isEmpty(), "Um título de Contas a Pagar deveria ter sido gerado");
        LancamentoFinanceiro faturaGerada = contasAPagar.get(0);
        
        assertEquals(TipoLancamento.PAGAR, faturaGerada.getTipoLancamento());
        assertEquals(0, BigDecimal.valueOf(300.00).compareTo(faturaGerada.getValorOriginal()), 
                "O valor do boleto da fatura deve ser R$ 300,00");
        assertEquals(StatusLancamento.ABERTO, faturaGerada.getStatus());

        // ==========================================
        // PASSO 3: Liquidar a fatura utilizando a Conta Bancária
        // ==========================================
        liquidacaoService.liquidarFatura(faturaGerada.getId(), contaId, BigDecimal.valueOf(300.00));

        // Valida se o saldo do banco diminuiu (R$ 5.000,00 - R$ 300,00 = R$ 4.700,00)
        ContaBancaria contaPosLiquidacao = contaBancariaRepository.findById(contaId).orElseThrow();
        assertEquals(0, BigDecimal.valueOf(4700.00).compareTo(contaPosLiquidacao.getSaldoAtual()), 
                "O saldo bancário deve ser R$ 4700,00");

        // Valida se o status da fatura mudou para liquidado
        LancamentoFinanceiro faturaLiquidada = financeiroRepository.findById(faturaGerada.getId()).orElseThrow();
        assertEquals(StatusLancamento.LIQUIDADO, faturaLiquidada.getStatus());

        // ==========================================
        // PASSO 4: Validar os lançamentos contábeis gerados
        // ==========================================
        List<LoteContabil> lotes = loteContabilRepository.findAll();
        assertFalse(lotes.isEmpty(), "A contabilidade deve ter gerado um lote para a liquidação");
        
        LoteContabil loteDeLiquidacao = lotes.get(0);
        assertEquals(OrigemLote.FINANCEIRO, loteDeLiquidacao.getOrigem());
        assertEquals(2, loteDeLiquidacao.getLinhas().size(), "O lote contábil deve ter exatamente duas linhas (partidas dobradas)");

        BigDecimal totalDebito = loteDeLiquidacao.getLinhas().stream()
                .filter(l -> l.getTipoLinha() == TipoLinhaContabil.DEBITO)
                .map(LancamentoContabil::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCredito = loteDeLiquidacao.getLinhas().stream()
                .filter(l -> l.getTipoLinha() == TipoLinhaContabil.CREDITO)
                .map(LancamentoContabil::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals(0, totalDebito.compareTo(BigDecimal.valueOf(300.00)), "O total de débitos contábeis deve ser R$ 300,00");
        assertEquals(0, totalCredito.compareTo(BigDecimal.valueOf(300.00)), "O total de créditos contábeis deve ser R$ 300,00");
    }
}
