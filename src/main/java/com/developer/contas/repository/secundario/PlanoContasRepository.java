package com.developer.contas.repository.secundario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.secundario.PlanoContas;

public interface PlanoContasRepository extends JpaRepository<PlanoContas, Long> {
	Optional<PlanoContas> findByCodigoEstrutural(String codigoEstrutural);
}
