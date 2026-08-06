package com.developer.contas.repository.secundario;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.developer.contas.entity.secundario.LancamentoFinanceiro;

@Repository
public interface LancamentoFinanceiroRepository extends JpaRepository<LancamentoFinanceiro, Long> {

    // 1. Projeção dos lançamentos futuros (Previstos) divididos por Tipo
    @Query(value = "SELECT l.data_vencimento AS data, " +
            "SUM(CASE WHEN l.tipo_lancamento = 'RECEBER' THEN (l.valor_original - l.valor_pago) ELSE 0 END) AS entradas, " +
            "SUM(CASE WHEN l.tipo_lancamento = 'PAGAR' THEN (l.valor_original - l.valor_pago) ELSE 0 END) AS saidas " +
            "FROM lancamentos_financeiros l " +
            "WHERE l.status IN ('ABERTO', 'PARCIAL') AND l.data_vencimento BETWEEN :inicio AND :fim " +
            "GROUP BY l.data_vencimento ORDER BY l.data_vencimento", nativeQuery = true)
    List<Object[]> buscarProjecaoPrevista(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    // 2. Projeção das movimentações bancárias efetivadas (Realizadas)
    @Query(value = "SELECT DATE(m.data_movimentacao) AS data, " +
            "SUM(CASE WHEN m.tipo_movimentacao = 'ENTRADA' THEN m.valor ELSE 0 END) AS entradas, " +
            "SUM(CASE WHEN m.tipo_movimentacao = 'SAIDA' THEN m.valor ELSE 0 END) AS saidas " +
            "FROM movimentacoes_bancarias m " +
            "WHERE DATE(m.data_movimentacao) BETWEEN :inicio AND :fim " +
            "GROUP BY DATE(m.data_movimentacao) ORDER BY data", nativeQuery = true)
    List<Object[]> buscarMovimentacaoRealizada(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}