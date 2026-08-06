package com.developer.contas.service;

import org.springframework.stereotype.Service;

import com.developer.contas.dto.PlanoContasDTO;
import com.developer.contas.entity.secundario.PlanoContas;
import com.developer.contas.generics.BaseService;
import com.developer.contas.repository.secundario.PlanoContasRepository;

@Service
public class PlanoContasService extends BaseService<PlanoContas, PlanoContasDTO, Long> {

	public PlanoContasService(PlanoContasRepository planoContasRepository) {
		super(planoContasRepository, PlanoContas.class, PlanoContasDTO.class);
	}

}
