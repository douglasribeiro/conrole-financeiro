package com.developer.contas.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.developer.contas.enuns.FormaPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE transacao SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
public class Transacao  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Id
	private Long id;
	
	@Column(length = 5)
	private String parcelas;
	
	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal valor;
	
	private LocalDate dtTransacao;
	
	@Column(length = 2)
	private String mesVencimento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCompra", referencedColumnName = "id", nullable = false)
	private Compra compra;
}
