package com.developer.contas.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.developer.contas.dto.MovimentacaoDTO;
import com.developer.contas.enuns.Movimento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE movimentacao SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
//@NamedQueries({
//	@NamedQuery(name = "movto.ultimoLancamento", query = "select m from Movimentacao m "
//			+ "where m.idBanco = :idBanco "
//			+ "and m.cricao = (select max(m2.criacao) from Movimentacao m2 where m2.idBanco = :idBanco)"
//	)
//})
public class Movimentacao extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idBanco", nullable = true, insertable = true, updatable = false)
	private Banco banco;
	
	@Column(length = 150)
	private String descricao;
	
	private BigDecimal valor;
	
	private BigDecimal saldo;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Movimento movimento;

	public Movimentacao(MovimentacaoDTO movimentacaoDTO) {
		this.id = movimentacaoDTO.getId();
		this.banco = movimentacaoDTO.getBanco();
		this.descricao = movimentacaoDTO.getDescricao();
		this.movimento = movimentacaoDTO.getMovimento();
		this.saldo = movimentacaoDTO.getSaldo();
	}
}
