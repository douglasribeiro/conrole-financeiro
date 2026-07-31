package com.developer.contas.entity.erpFinanceiro;

import com.developer.contas.entity.erpFinanceiro.enuns.TipoLinhaContabil;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "lancamentos_contabeis")
@Data
public class LancamentoContabil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lote_contabil_id", nullable = false)
    private LoteContabil loteContabil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_contas_id", nullable = false)
    private PlanoContas planoContas;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_linha", nullable = false, length = 10)
    private TipoLinhaContabil tipoLinha;

    @Column(nullable = false)
    private BigDecimal valor;
}
