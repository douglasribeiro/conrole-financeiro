package com.developer.contas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.Pagar;

public interface ContasRepository extends JpaRepository<Pagar, Long> {

}
