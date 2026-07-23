package com.developer.contas.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.developer.contas.dto.CompraDTO;
import com.developer.contas.enuns.FormaPagamento;

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
@SQLDelete(sql = "UPDATE compra SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
public class Compra  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Id
	private Long id;
	
	@Column(length = 100, nullable = false)
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FormaPagamento formaPagamento;

	private Integer parcelas;
	
	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal valor;
	
	private LocalDate vencimento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCartao", nullable = true, insertable = true, updatable = false)
	private Cartao cartao;
	
	public Compra(CompraDTO dto) {
		this.id = dto.getId();
		this.descricao = dto.getDescricao();
		this.formaPagamento = dto.getFormaPagamento();
		this.valor = dto.getValor();
		this.vencimento = dto.getVencimento();
		this.cartao = dto.getCartao();
	}
}
