package com.developer.contas.entity.secundario;

import com.developer.contas.dto.EmpresaDTO;
import com.developer.contas.entity.primario.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empresas", schema = "secundario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razao_social", nullable = false, length = 150)
    private String razaoSocial;

    @Column(name = "nome_fantasia", length = 100)
    private String nomeFantasia;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    public Empresa(EmpresaDTO empresaDTO) {
    	this.id = empresaDTO.getId();
    	this.razaoSocial = empresaDTO.getRazaoSocial();
    	this.nomeFantasia = empresaDTO.getNomeFantasia();
    	this.cnpj = empresaDTO.getCnpj();
    }
}
