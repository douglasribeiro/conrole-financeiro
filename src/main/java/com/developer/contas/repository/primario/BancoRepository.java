package com.developer.contas.repository.primario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developer.contas.entity.primario.Banco;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long>{

}
