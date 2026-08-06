package com.developer.contas.repository.secundario;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.secundario.LoteContabil;

public interface LoteContabilRepository extends JpaRepository<LoteContabil, Long> {
}
