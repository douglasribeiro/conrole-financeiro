package com.developer.contas.service;

import org.springframework.stereotype.Service;

import com.developer.contas.dto.PagarDTO;
import com.developer.contas.entity.Pagar;
import com.developer.contas.generics.BaseService;

@Service
public class PagarService extends BaseService<Pagar, PagarDTO> {

	public PagarService() {
		super(Pagar.class, PagarDTO.class);
	}

}
