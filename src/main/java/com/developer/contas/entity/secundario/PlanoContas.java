package com.developer.contas.entity.secundario;

import com.developer.contas.dto.PlanoContasDTO;
import com.developer.contas.entity.primario.BaseEntity;
import com.developer.contas.enuns.NaturezaContabil;
import com.developer.contas.enuns.TipoContaContabil;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plano_contas", schema = "secundario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanoContas extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_estrutural", nullable = false, unique = true, length = 30)
    private String codigoEstrutural;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoContaContabil tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NaturezaContabil natureza;

    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Boolean analitica = true;
    
    public PlanoContas(PlanoContasDTO planoContasDTO) {
    	this.id = planoContasDTO.getId();
    	this.codigoEstrutural = planoContasDTO.getCodigoEstrutural();
    	this.descricao = planoContasDTO.getDescricao();
    	this.tipo = planoContasDTO.getTipo();
    	this.natureza = planoContasDTO.getNatureza();
    	this.nivel = planoContasDTO.getNivel();
    	this.analitica = planoContasDTO.getAnalitica();
    }
}
