package com.developer.contas.dto;

import com.developer.contas.entity.secundario.Parceiro;
import com.developer.contas.entity.secundario.TipoParceiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParceiroDTO {

	private Long id;
    private String nome;
    private String cpfCnpj;
    private TipoParceiro tipo;
    private String email;
    private String telefone;
    
    public ParceiroDTO(Parceiro parceiro) {
    	this.id = parceiro.getId();
    	this.nome = parceiro.getNome();
    	this.cpfCnpj = parceiro.getCpfCnpj();
    	this.tipo = parceiro.getTipo();
    	this.email = parceiro.getEmail();
    	this.telefone = parceiro.getTelefone();
    }
    
    
}
