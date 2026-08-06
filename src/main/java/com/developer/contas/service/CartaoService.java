package com.developer.contas.service;

import org.springframework.stereotype.Service;

import com.developer.contas.dto.CartaoDTO;
import com.developer.contas.entity.primario.Cartao;
import com.developer.contas.generics.BaseService;
import com.developer.contas.repository.primario.CartaoRepository;

@Service
public class CartaoService extends BaseService<Cartao, CartaoDTO, Long> {

	public CartaoService(CartaoRepository cartaoRepository) {
		super(cartaoRepository, Cartao.class, CartaoDTO.class);
	}

}
