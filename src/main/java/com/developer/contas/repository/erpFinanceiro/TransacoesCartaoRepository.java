package com.developer.contas.repository.erpFinanceiro;

import com.developer.contas.entity.erpFinanceiro.TransacoesCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransacoesCartaoRepository extends JpaRepository<TransacoesCartao, Long> {
    // Procura as transações de um cartão específico pertencentes a uma fatura alvo (Ex: "2026-08")
    List<TransacoesCartao> findByCartaoCreditoIdAndFaturaMesAno(Long cartaoId, String faturaMesAno);
}
