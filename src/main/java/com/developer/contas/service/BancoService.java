package com.developer.contas.service;

import org.springframework.stereotype.Service;

import com.developer.contas.dto.BancoDTO;
import com.developer.contas.entity.Banco;
import com.developer.contas.generics.BaseService;

@Service
public class BancoService extends BaseService<Banco, BancoDTO> {

	public BancoService() {
		super(Banco.class, BancoDTO.class);
	}

}
