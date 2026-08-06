package com.developer.contas.entity.secundario;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.developer.contas.enuns.TipoMovimentacao;

@Entity
@Table(name = "movimentacoes_bancarias", schema = "secundario")
@Data
public class MovimentacaoBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_bancaria_id")
    private ContaBancaria contaBancaria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lancamento_financeiro_id")
    private LancamentoFinanceiro lancamentoFinanceiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao", nullable = false, length = 10)
    private TipoMovimentacao tipoMovimentacao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "data_movimentacao")
    private LocalDateTime dataMovimentacao = LocalDateTime.now();

    @Column(nullable = false, length = 255)
    private String historico;
}
