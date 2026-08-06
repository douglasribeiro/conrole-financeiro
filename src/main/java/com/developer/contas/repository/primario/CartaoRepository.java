package com.developer.contas.repository.primario;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.primario.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{

}
