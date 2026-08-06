package com.developer.contas.entity.secundario;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes_cartao", schema = "secundario")
@Data
public class TransacoesCartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartao_credito_id", nullable = false)
    private CartaoCredito cartaoCredito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lancamento_financeiro_id")
    private LancamentoFinanceiro lancamentoFinanceiro;

    @Column(nullable = false, length = 100)
    private String estabelecimento;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "data_transacao", nullable = false)
    private LocalDateTime dataTransacao;

    @Column(name = "fatura_mes_ano", nullable = false, length = 7)
    private String faturaMesAno; // Ex: "2026-08"

    @Column(name = "numero_parcela")
    private Integer numeroParcela = 1;

    @Column(name = "total_parcelas")
    private Integer totalParcelas = 1;
}
