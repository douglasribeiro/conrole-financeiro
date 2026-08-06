package com.developer.contas.entity.secundario;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

import com.developer.contas.enuns.TipoLinhaContabil;

@Entity
@Table(name = "lancamentos_contabeis", schema = "secundario")
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
