package com.developer.contas.repository.secundario;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.secundario.ContaBancaria;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {
}
