package com.developer.contas.entity.erpFinanceiro;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "cartoes_credito")
@Data
public class CartaoCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_bancaria_id", nullable = false)
    private ContaBancaria contaBancaria;

    @Column(name = "nome_cartao", nullable = false, length = 50)
    private String nomeCartao;

    @Column(length = 20)
    private String bandeira;

    @Column(name = "limite_total", nullable = false)
    private BigDecimal limiteTotal;

    @Column(name = "limite_disponivel", nullable = false)
    private BigDecimal limiteDisponivel;

    @Column(name = "dia_fechamento", nullable = false)
    private Integer diaFechamento;

    @Column(name = "dia_vencimento", nullable = false)
    private Integer diaVencimento;
}
