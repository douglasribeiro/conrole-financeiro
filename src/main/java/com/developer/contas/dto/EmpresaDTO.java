package com.developer.contas.dto;

import com.developer.contas.entity.secundario.Empresa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaDTO {

	private Long id;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    
    public EmpresaDTO(Empresa empresa) {
    	this.id = empresa.getId();
    	this.razaoSocial = empresa.getRazaoSocial();
    	this.nomeFantasia = empresa.getNomeFantasia();
    	this.cnpj = empresa.getCnpj();
    }
}
