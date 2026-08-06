package com.developer.contas.dto;

import java.math.BigDecimal;

import com.developer.contas.entity.secundario.ContaBancaria;
import com.developer.contas.entity.secundario.Empresa;
import com.developer.contas.enuns.TipoContaBancaria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaBancariaDTO {

	private Long id;
    private Empresa empresa;
    private String bancoCodigo;
    private String nomeConta;
    private String agencia;
    private String numeroConta;
    private TipoContaBancaria tipoConta;
    private BigDecimal saldoAtual = BigDecimal.ZERO;
    
    public ContaBancariaDTO(ContaBancaria contaBancaria) {
    	this.id = contaBancaria.getId();
    	this.empresa = contaBancaria.getEmpresa();
    	this.bancoCodigo = contaBancaria.getBancoCodigo();
    	this.nomeConta = contaBancaria.getNomeConta();
    	this.agencia = contaBancaria.getAgencia();
    	this.numeroConta = contaBancaria.getNumeroConta();
    	this.tipoConta = contaBancaria.getTipoConta();
    	this.saldoAtual = contaBancaria.getSaldoAtual();
    }
}
