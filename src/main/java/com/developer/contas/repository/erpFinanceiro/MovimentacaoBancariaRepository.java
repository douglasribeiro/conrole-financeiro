package com.developer.contas.repository.erpFinanceiro;

import com.developer.contas.entity.erpFinanceiro.MovimentacaoBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoBancariaRepository extends JpaRepository<MovimentacaoBancaria, Long> {
}
