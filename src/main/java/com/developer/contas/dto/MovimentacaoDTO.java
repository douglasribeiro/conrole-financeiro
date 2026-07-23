package com.developer.contas.dto;

import java.math.BigDecimal;

import com.developer.contas.entity.Banco;
import com.developer.contas.entity.Movimentacao;
import com.developer.contas.enuns.Movimento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoDTO {

	private Long id;
	
	private Banco banco;
	
	private String descricao;
	
	private BigDecimal valor;
	
	private BigDecimal saldo;
	
	private Movimento movimento;
	
	public MovimentacaoDTO(Movimentacao movimentacao) {
		this.id = movimentacao.getId();
		this.banco = movimentacao.getBanco();
		this.descricao = movimentacao.getDescricao();
		this.movimento = movimentacao.getMovimento();
		this.saldo = movimentacao.getSaldo();
	}

}
