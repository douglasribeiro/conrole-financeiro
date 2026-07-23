package com.developer.contas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.Banco;

public interface BancoRepository extends JpaRepository<Banco, Long>{

}
