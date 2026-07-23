package com.developer.contas.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    @Column(updatable = false)
    private LocalDateTime criacao;

    private LocalDateTime alteracao;
    
    private Boolean ativo;
    
    @Column(length = 50)
    private String usuario;
    
    @PreUpdate
    public void preUpdate() {
        alteracao = LocalDateTime.now();
        ativo = true;
    }
    
    @PrePersist
    public void preInsert() {
    	criacao = LocalDateTime.now();
    	ativo = true;
    }
}
