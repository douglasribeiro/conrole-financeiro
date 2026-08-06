package com.developer.contas.dto;

import java.math.BigDecimal;

import com.developer.contas.entity.secundario.CartaoCredito;
import com.developer.contas.entity.secundario.ContaBancaria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartaoCreditoDTO {

	private Long id;
    private ContaBancaria contaBancaria;
    private String nomeCartao;
    private String bandeira;
    private BigDecimal limiteTotal;
    private BigDecimal limiteDisponivel;
    private Integer diaFechamento;
    private Integer diaVencimento;
    
    public CartaoCreditoDTO(CartaoCredito cartaoCredito) {
    	this.id = cartaoCredito.getId();
    	this.contaBancaria = cartaoCredito.getContaBancaria();
    	this.nomeCartao = cartaoCredito.getNomeCartao();
    	this.bandeira = cartaoCredito.getBandeira();
    	this.limiteTotal = cartaoCredito.getLimiteTotal();
    	this.limiteDisponivel = cartaoCredito.getLimiteDisponivel();
    	this.diaFechamento = cartaoCredito.getDiaFechamento();
    	this.diaVencimento = cartaoCredito.getDiaVencimento();
    }
}
