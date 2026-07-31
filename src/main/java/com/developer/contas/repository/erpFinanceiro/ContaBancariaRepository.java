package com.developer.contas.repository.erpFinanceiro;

import com.developer.contas.entity.erpFinanceiro.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {
}
