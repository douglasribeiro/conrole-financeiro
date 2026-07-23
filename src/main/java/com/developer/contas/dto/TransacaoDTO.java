package com.developer.contas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.developer.contas.entity.Compra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransacaoDTO {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String parcelas;
	
	private BigDecimal valor;
	
	private LocalDate dtTransacao;
	
	private String mesVencimento;
	
	private Compra compra;
}
