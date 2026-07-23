package com.developer.contas.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Movimento {

	DEBITO(1, "Debito"),
	CREDITO(2, "Credito");
	
	private final int id;
	private final String descricao;
	
	Movimento(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
	@JsonValue
    public int getId() { return id; }
	
	public static Movimento doId(int id) {
		for (Movimento fp : Movimento.values()) {
			if (fp.getId() == id) {
				return fp;
			}
		}
		throw new IllegalArgumentException("Código de movimento inválido: " + id);
	}
}
