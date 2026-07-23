package com.developer.contas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.developer.contas.entity.Cartao;
import com.developer.contas.enuns.FormaPagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {

	private Long id;
	
	private String descricao;
	
	private FormaPagamento formaPagamento;

	private Integer parcelas;
	
	private BigDecimal valor;
	
	private LocalDate vencimento;
	
	private Cartao cartao;
}