package com.developer.contas.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE cartao SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
public class Cartao  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Id
	private Long id;
	
	@Column(length = 50)
	private String apelido;
	
	@Column(length =  30)
	private String numero;
	
	@Column(length = 2)
	private String fechamento;
	
	@Column(length = 2)
	private String vencimento;
	
	@Column(precision = 19, scale = 2)
	private BigDecimal limite;
	
	@Column(length = 2)
	private String mesCorrente;
	
	@OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Compra> compras = new ArrayList<>();
	
	@Transient
	private Double valorFatura;
}
