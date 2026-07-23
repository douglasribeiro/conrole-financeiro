package com.developer.contas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {

}
