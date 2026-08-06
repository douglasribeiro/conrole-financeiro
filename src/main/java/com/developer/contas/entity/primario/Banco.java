package com.developer.contas.entity.primario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import jakarta.persistence.Table;
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
@Table(name = "bancos", schema = "primario")
public class Banco  extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	@Column(length = 50, nullable = false, unique = true)
	private String nome;
	
	private BigDecimal saldo;
	
	private BigDecimal limite;

    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dtAbertura;

	@Column(length = 10, nullable = false)
	private String nuAgencia;

	@Column(length = 15, nullable = false, unique = true)
	private String nuConta;

	@Column(length = 35)
	private String noGerente;

	@Column(length = 50)
	private String endereco;

	@Column(length = 20)
	private String telefone;

	@JsonManagedReference
	@OneToMany(mappedBy = "banco", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<Movimentacao> movimentacoes = new ArrayList<>();
	
	public Banco(BancoDTO bancoDTO) {
		this.id = bancoDTO.getId();
		this.nome = bancoDTO.getNome();
		this.saldo = bancoDTO.getSaldo();
		this.dtAbertura = bancoDTO.getDtAbertura();
		this.limite = bancoDTO.getLimite();
		this.movimentacoes = bancoDTO.getMovimentacoes();
		this.nuAgencia = bancoDTO.getNuAgencia();
		this.nuConta = bancoDTO.getNuConta();
		this.noGerente = bancoDTO.getNoGerente();
		this.endereco = bancoDTO.getEndereco();
		this.telefone = bancoDTO.getTelefone();
	}
}
