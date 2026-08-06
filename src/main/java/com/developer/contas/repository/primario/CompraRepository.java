package com.developer.contas.repository.primario;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.primario.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {

}
