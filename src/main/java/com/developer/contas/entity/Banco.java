package com.developer.contas.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.developer.contas.dto.BancoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE banco SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
public class Banco  extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	@Column(length = 50, nullable = false, unique = true)
	private String nome;
	
	private BigDecimal saldo;
	
	private LocalDateTime atualizado;
	
	private BigDecimal limite;	
	
	@JsonManagedReference
	@OneToMany(mappedBy = "banco", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<Movimentacao> movimentacoes = new ArrayList<>();
	
	public Banco(BancoDTO bancoDTO) {
		this.id = bancoDTO.getId();
		this.nome = bancoDTO.getNome();
		this.saldo = bancoDTO.getSaldo();
		this.atualizado = bancoDTO.getAtualizado();
		this.limite = bancoDTO.getLimite();
	}
}
