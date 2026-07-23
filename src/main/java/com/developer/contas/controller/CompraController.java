package com.developer.contas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.contas.dto.CompraDTO;
import com.developer.contas.generics.BaseController;
import com.developer.contas.service.CompraService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/compra")
@RequiredArgsConstructor
public class CompraController extends BaseController<CompraDTO,	CompraService>{
	
	 private static final Logger log = LoggerFactory.getLogger(CompraController.class);


	@GetMapping("/on")
	public String estaOn() {
		log.info("Serviço de compras esta no ar.");
		return "Serviço esta no ar.";
	}

}
