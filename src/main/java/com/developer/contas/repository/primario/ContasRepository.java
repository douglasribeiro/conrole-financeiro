package com.developer.contas.repository.primario;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.contas.entity.primario.Pagar;

public interface ContasRepository extends JpaRepository<Pagar, Long> {

}
