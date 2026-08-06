package com.developer.contas.service;

import org.springframework.stereotype.Service;

import com.developer.contas.dto.EmpresaDTO;
import com.developer.contas.entity.secundario.Empresa;
import com.developer.contas.generics.BaseService;
import com.developer.contas.repository.secundario.EmpresaRepository;

@Service
public class EmpresaService extends BaseService<Empresa, EmpresaDTO, Long> {

	public EmpresaService(EmpresaRepository empresaRepository) {
		super(empresaRepository, Empresa.class, EmpresaDTO.class);
	}

}
