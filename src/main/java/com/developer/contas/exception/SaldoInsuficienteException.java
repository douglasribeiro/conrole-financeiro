package com.developer.contas.exception;

public class SaldoInsuficienteException extends RuntimeException {
    
  private static final long serialVersionUID = 1L;

	// Construtor padrão sem mensagem
    public SaldoInsuficienteException() {
        super();
    }

    // Construtor principal que aceita a mensagem de erro personalizada
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
