package com.developer.contas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.developer.contas.entity.Banco;
import com.developer.contas.entity.Movimentacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BancoDTO {

	private Long id;
	
	private String nome;
	
	private BigDecimal saldo;
	
	private LocalDateTime atualizado;
	
	private BigDecimal limite;
	
	private List<Movimentacao> movimentacoes = new ArrayList<>();
	
	public BancoDTO(Banco banco) {
		this.id = banco.getId();
		this.nome = banco.getNome();
		this.saldo = banco.getSaldo();
		this.atualizado = banco.getAtualizado();
		this.limite = banco.getLimite();
		this.movimentacoes = banco.getMovimentacoes();
	}
}
