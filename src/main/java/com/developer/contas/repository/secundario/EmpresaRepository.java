package com.developer.contas.repository.secundario;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.secundario.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
