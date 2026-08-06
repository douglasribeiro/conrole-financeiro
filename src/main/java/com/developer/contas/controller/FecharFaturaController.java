package com.developer.contas.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.contas.service.CartaoCreditoService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/fechamento")
@RequiredArgsConstructor
public class FecharFaturaController {

	private final CartaoCreditoService cartaoCreditoService;
	
	@GetMapping
	public void fechamento() {
		cartaoCreditoService.fecharFaturasAgendadas();
	}
	
}
