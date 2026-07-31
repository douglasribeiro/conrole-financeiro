package com.developer.contas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.developer.contas.entity.Banco;
import com.developer.contas.entity.Movimentacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
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

    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dtAbertura;
	
	private BigDecimal limite;

	private String nuAgencia;

	private String nuConta;

	private String noGerente;

	private String endereco;

	private String telefone;
	
	private List<Movimentacao> movimentacoes = new ArrayList<>();
	
	public BancoDTO(Banco banco) {
		this.id = banco.getId();
		this.nome = banco.getNome();
		this.saldo = banco.getSaldo();
		this.dtAbertura = banco.getDtAbertura();
		this.limite = banco.getLimite();
		this.movimentacoes = banco.getMovimentacoes();
		this.nuAgencia = banco.getNuAgencia();
		this.nuConta = banco.getNuConta();
		this.noGerente = banco.getNoGerente();
		this.endereco = banco.getEndereco();
		this.telefone = banco.getTelefone();
	}
}
