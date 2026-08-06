package com.developer.contas.service;

import org.springframework.stereotype.Service;

import com.developer.contas.dto.ContaBancariaDTO;
import com.developer.contas.entity.secundario.ContaBancaria;
import com.developer.contas.generics.BaseService;
import com.developer.contas.repository.secundario.ContaBancariaRepository;

@Service
public class ContaBancariaService extends BaseService<ContaBancaria, ContaBancariaDTO, Long> {

	public ContaBancariaService(ContaBancariaRepository contaBancariaRepository) {
		super(contaBancariaRepository, ContaBancaria.class, ContaBancariaDTO.class);
	}

}
