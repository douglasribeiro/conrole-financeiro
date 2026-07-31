package com.developer.contas.entity.erpFinanceiro;

import com.developer.contas.entity.erpFinanceiro.enuns.NaturezaContabil;
import com.developer.contas.entity.erpFinanceiro.enuns.TipoContaContabil;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "plano_contas")
@Data
public class PlanoContas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_estrutural", nullable = false, unique = true, length = 30)
    private String codigoEstrutural;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoContaContabil tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NaturezaContabil natureza;

    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Boolean analitica = true;
}
