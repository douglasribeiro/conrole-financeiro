package com.developer.contas.repository.secundario;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.secundario.Parceiro;

public interface FornecedorRepository extends JpaRepository<Parceiro, Long> {

}
