package com.developer.contas.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.contas.dto.ParceiroDTO;
import com.developer.contas.generics.BaseController;
import com.developer.contas.service.ParceiroService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/parceiro")
@RequiredArgsConstructor
public class ParceiroController extends BaseController<ParceiroDTO,	ParceiroService> {

	@GetMapping("/on")
	public String estaoon() {
		return "retorno metodo parceiro";
	}
}
