package com.developer.contas.entity.erpFinanceiro;

import com.developer.contas.entity.erpFinanceiro.enuns.TipoContaBancaria;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "contas_bancarias")
@Data
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @Column(name = "banco_codigo", nullable = false, length = 10)
    private String bancoCodigo;

    @Column(name = "nome_conta", nullable = false, length = 50)
    private String nomeConta;

    @Column(nullable = false, length = 10)
    private String agencia;

    @Column(name = "numero_conta", nullable = false, length = 20)
    private String numeroConta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta", nullable = false, length = 20)
    private TipoContaBancaria tipoConta;

    @Column(name = "saldo_atual")
    private BigDecimal saldoAtual = BigDecimal.ZERO;
}
