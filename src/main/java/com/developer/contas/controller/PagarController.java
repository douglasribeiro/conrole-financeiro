package com.developer.contas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.contas.dto.PagarDTO;
import com.developer.contas.generics.BaseController;
import com.developer.contas.service.PagarService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/pagar")
@RequiredArgsConstructor
public class PagarController extends BaseController<PagarDTO,	PagarService>{

	private static final Logger log = LoggerFactory.getLogger(PagarController.class);
	
	@GetMapping("/on")
	public String estaOn() {
		log.info("O serviço de pagar esra on.");
		return "Serviço esta no ar.";
	}
}
