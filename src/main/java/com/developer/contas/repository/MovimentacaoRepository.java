package com.developer.contas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.developer.contas.entity.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long>{
	
	@Query("SELECT m FROM Movimentacao m " +
		       "WHERE m.banco.id = :idBanco " +
		       "AND m.criacao = (SELECT MAX(m2.criacao) FROM Movimentacao m2 WHERE m2.banco.id = :idBanco)")
		Optional<Movimentacao> buscarUltimoLancamento(@Param("idBanco") Long idBanco);
	
	Optional<Movimentacao> findFirstByBancoIdOrderByCriacaoDesc(Long idBanco);

}
