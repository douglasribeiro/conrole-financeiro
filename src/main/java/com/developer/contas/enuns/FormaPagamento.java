package com.developer.contas.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FormaPagamento {

	AVISTA(1, "A Vista"),
	PIX(2, "Pix"),
	PIXPARCELADO(3, "Pix Parcelado"),
	DEBITO(4, "Débito"),
	CREDITO(5, "Crédito"),
	CREDITOPARCELADO(6, "Crédito Parcelado"),
	CREDIARIO(7, "Crediário"),
	BOLETO(8, "Boleto");
	
	private final int id;
	private final String descricao;
	
	FormaPagamento(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
	@JsonValue
    public int getId() { return id; }
	
	public static FormaPagamento doId(int id) {
		for (FormaPagamento fp : FormaPagamento.values()) {
			if (fp.getId() == id) {
				return fp;
			}
		}
		throw new IllegalArgumentException("Código de FormaPagamento inválido: " + id);
	}
}
