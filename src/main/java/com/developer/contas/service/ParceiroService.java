package com.developer.contas.service;

import org.springframework.stereotype.Service;

import com.developer.contas.dto.ParceiroDTO;
import com.developer.contas.entity.secundario.Parceiro;
import com.developer.contas.generics.BaseService;
import com.developer.contas.repository.secundario.ParceiroRepository;

@Service
public class ParceiroService extends BaseService<Parceiro, ParceiroDTO, Long> {

	public ParceiroService(ParceiroRepository parceiroRepository) {
		super(parceiroRepository, Parceiro.class, ParceiroDTO.class);
	}

}
