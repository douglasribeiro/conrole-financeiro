package com.developer.contas.repository.primario;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.primario.Compra;
import com.developer.contas.entity.primario.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
