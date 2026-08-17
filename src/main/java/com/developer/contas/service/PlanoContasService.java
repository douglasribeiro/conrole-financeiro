package com.developer.contas.service;

import com.developer.contas.repository.secundario.BancoRepository;
import org.springframework.stereotype.Service;

import com.developer.contas.dto.PlanoContasDTO;
import com.developer.contas.entity.secundario.PlanoContas;
import com.developer.contas.generics.BaseService;

@Service
public class PlanoContasService extends BaseService<PlanoContas, PlanoContasDTO, Long> {

	public PlanoContasService(BancoRepository planoContasRepository) {
		super(planoContasRepository, PlanoContas.class, PlanoContasDTO.class);
	}

}
