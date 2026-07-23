package com.developer.contas.service;

import org.springframework.stereotype.Service;

import com.developer.contas.dto.CartaoDTO;
import com.developer.contas.entity.Cartao;
import com.developer.contas.generics.BaseService;

@Service
public class CartaoService extends BaseService<Cartao, CartaoDTO> {

	public CartaoService() {
		super(Cartao.class, CartaoDTO.class);
	}

}
