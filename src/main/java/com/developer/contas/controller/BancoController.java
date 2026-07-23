package com.developer.contas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.contas.dto.BancoDTO;
import com.developer.contas.generics.BaseController;
import com.developer.contas.service.BancoService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/banco")
@RequiredArgsConstructor
public class BancoController extends BaseController<BancoDTO,	BancoService>{
	
	private static final Logger log = LoggerFactory.getLogger(BancoController.class);

	@GetMapping("/on")
	public String estaOn() {
		log.info("O serviço de Banco esta no ar.");
		return "Serviço de banco esta no ar.";
	}
}
