package com.developer.contas.service;

import com.developer.contas.repository.secundario.BancoRepository;
import org.springframework.stereotype.Service;

import com.developer.contas.dto.BancoDTO;
import com.developer.contas.entity.primario.Banco;
import com.developer.contas.generics.BaseService;

@Service
public class BancoService extends BaseService<Banco, BancoDTO, Long> {

	public BancoService(BancoRepository bancoRepository) {
		super(bancoRepository, Banco.class, BancoDTO.class);
	}

}
