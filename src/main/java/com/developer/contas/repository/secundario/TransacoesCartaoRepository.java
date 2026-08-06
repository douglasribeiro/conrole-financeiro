package com.developer.contas.repository.secundario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developer.contas.entity.secundario.TransacoesCartao;

import java.util.List;

@Repository
public interface TransacoesCartaoRepository extends JpaRepository<TransacoesCartao, Long> {
    // Procura as transações de um cartão específico pertencentes a uma fatura alvo (Ex: "2026-08")
    List<TransacoesCartao> findByCartaoCreditoIdAndFaturaMesAno(Long cartaoId, String faturaMesAno);
}
