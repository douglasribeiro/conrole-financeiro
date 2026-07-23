package com.developer.contas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.contas.dto.CartaoDTO;
import com.developer.contas.generics.BaseController;
import com.developer.contas.service.CartaoService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cartao")
@RequiredArgsConstructor
public class CartaoController extends BaseController<CartaoDTO,	CartaoService>{

	private static final Logger log = LoggerFactory.getLogger(CartaoController.class);
	
	@GetMapping("/on")
	public String estaOn() {
		log.info("Serviço de cartão esta no ar.");
		return "Serviço de cartão esta no ar.";
	}
}
