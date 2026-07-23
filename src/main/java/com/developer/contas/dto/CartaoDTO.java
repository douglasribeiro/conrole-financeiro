package com.developer.contas.dto;

import java.util.ArrayList;
import java.util.List;

import com.developer.contas.entity.Compra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoDTO {

	private Long id;
	private String apelido;
	private String numero;
	private String fechamento;
	private String vencimento;
	private Double limite;
	private Double valorFatura;
	private String mesCorrente;
	private List<Compra> compras = new ArrayList<>();
}
