package com.developer.contas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{

}
