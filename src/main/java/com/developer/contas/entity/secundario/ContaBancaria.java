package com.developer.contas.entity.secundario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.developer.contas.dto.ContaBancariaDTO;
import com.developer.contas.entity.primario.BaseEntity;
import com.developer.contas.enuns.TipoContaBancaria;

@Entity
@Table(name = "contas_bancarias", schema = "secundario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaBancaria extends BaseEntity {
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
    
    
    public ContaBancaria(ContaBancariaDTO contaBancariaDTO) {
    	this.id = contaBancariaDTO.getId();
    	this.empresa = contaBancariaDTO.getEmpresa();
    	this.bancoCodigo = contaBancariaDTO.getBancoCodigo();
    	this.nomeConta = contaBancariaDTO.getNomeConta();
    	this.agencia = contaBancariaDTO.getAgencia();
    	this.numeroConta = contaBancariaDTO.getNumeroConta();
    	this.tipoConta = contaBancariaDTO.getTipoConta();
    	this.saldoAtual = contaBancariaDTO.getSaldoAtual();
    }
}
