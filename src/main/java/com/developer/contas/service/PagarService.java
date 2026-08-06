package com.developer.contas.service;

import org.springframework.stereotype.Service;

import com.developer.contas.dto.PagarDTO;
import com.developer.contas.entity.primario.Pagar;
import com.developer.contas.generics.BaseService;
import com.developer.contas.repository.primario.PagarRepository;

@Service
public class PagarService extends BaseService<Pagar, PagarDTO, Long> {

	public PagarService(PagarRepository pagarRepository) {
		super(pagarRepository, Pagar.class, PagarDTO.class);
	}

}
