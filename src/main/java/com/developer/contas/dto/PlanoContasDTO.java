package com.developer.contas.dto;

import com.developer.contas.entity.secundario.PlanoContas;
import com.developer.contas.enuns.NaturezaContabil;
import com.developer.contas.enuns.TipoContaContabil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanoContasDTO {

	private Long id;
    private String codigoEstrutural;
    private String descricao;
    private TipoContaContabil tipo;
    private NaturezaContabil natureza;
    private Integer nivel;
    private Boolean analitica = true;
    
    public PlanoContasDTO(PlanoContas planoContas) {
    	this.id = planoContas.getId();
    	this.codigoEstrutural = planoContas.getCodigoEstrutural();
    	this.descricao = planoContas.getDescricao();
    	this.tipo = planoContas.getTipo();
    	this.natureza = planoContas.getNatureza();
    	this.nivel = planoContas.getNivel();
    	this.analitica = planoContas.getAnalitica();
    }
}
