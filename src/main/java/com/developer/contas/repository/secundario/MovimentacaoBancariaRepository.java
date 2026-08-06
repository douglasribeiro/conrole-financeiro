package com.developer.contas.repository.secundario;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.secundario.MovimentacaoBancaria;

public interface MovimentacaoBancariaRepository extends JpaRepository<MovimentacaoBancaria, Long> {
}
