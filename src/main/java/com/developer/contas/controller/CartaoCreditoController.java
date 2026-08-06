package com.developer.contas.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.contas.dto.CartaoCreditoDTO;
import com.developer.contas.generics.BaseController;
import com.developer.contas.service.CartaoCreditoService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cc")
@RequiredArgsConstructor
public class CartaoCreditoController extends BaseController<CartaoCreditoDTO, CartaoCreditoService> {

	private final CartaoCreditoService cartaoCreditoService;
	
	@PostMapping("/compra")
	public void registrarCompra(@RequestBody CompraDTO compraDTO) {
		cartaoCreditoService.registrarCompra(compraDTO.cartaoId, compraDTO.estabelecimento, compraDTO.valor, compraDTO.parcelas);
	}
	
	@GetMapping("/on")
	public String estaOn() {
		return "Serviço cartao credito esta on.";
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	private static class CompraDTO {
		private Long cartaoId;
		private String estabelecimento;
		private BigDecimal valor;
		private Integer parcelas;
	}
}
