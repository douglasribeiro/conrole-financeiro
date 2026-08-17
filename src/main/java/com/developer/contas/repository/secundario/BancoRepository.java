package com.developer.contas.repository.secundario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developer.contas.entity.secundario.Banco;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long>{

}
