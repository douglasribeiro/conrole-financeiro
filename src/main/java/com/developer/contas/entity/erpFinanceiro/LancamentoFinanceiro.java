package com.developer.contas.entity.erpFinanceiro;

import com.developer.contas.entity.erpFinanceiro.enuns.StatusLancamento;
import com.developer.contas.entity.erpFinanceiro.enuns.TipoLancamento;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamentos_financeiros")
@Data
public class LancamentoFinanceiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parceiro_id", nullable = false)
    private Parceiro parceiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_contas_id", nullable = false)
    private PlanoContas planoContas;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_lancamento", nullable = false, length = 10)
    private TipoLancamento tipoLancamento;

    @Column(name = "numero_documento", length = 50)
    private String numeroDocumento;

    @Column(nullable = false, length = 200)
    private String descricao;

    @Column(name = "valor_original", nullable = false)
    private BigDecimal valorOriginal;

    @Column(name = "valor_pago")
    private BigDecimal valorPago = BigDecimal.ZERO;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusLancamento status = StatusLancamento.ABERTO;
}
