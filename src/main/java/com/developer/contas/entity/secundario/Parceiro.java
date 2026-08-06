package com.developer.contas.entity.secundario;

import com.developer.contas.dto.ParceiroDTO;
import com.developer.contas.entity.primario.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parceiros", schema = "secundario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parceiro extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "cpf_cnpj", nullable = false, unique = true, length = 14)
    private String cpfCnpj;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoParceiro tipo;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;
    
    public Parceiro(ParceiroDTO parceiroDTO) {
    	this.id = parceiroDTO.getId();
    	this.nome = parceiroDTO.getNome();
    	this.cpfCnpj = parceiroDTO.getCpfCnpj();
    	this.tipo = parceiroDTO.getTipo();
    	this.email = parceiroDTO.getEmail();
    	this.telefone = parceiroDTO.getTelefone();
    }
}
