package com.developer.contas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.Compra;
import com.developer.contas.entity.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
