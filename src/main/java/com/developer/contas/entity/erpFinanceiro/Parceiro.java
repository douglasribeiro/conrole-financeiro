package com.developer.contas.entity.erpFinanceiro;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parceiros")
@Data
public class Parceiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "cpf_cnpj", nullable = false, unique = true, length = 14)
    private String cpfCnpj;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoParceiro tipo;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;

    private Boolean ativo = true;
}
