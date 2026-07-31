package com.developer.contas.exception.erpFinanceiro;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção customizada para sinalizar que um recurso solicitado (Entidade)
 * não foi encontrado na base de dados.
 *
 * A anotação @ResponseStatus garante que, caso esta exceção não seja capturada,
 * o Spring Boot responda automaticamente com o Status HTTP 404 (Not Found).
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Construtor padrão que aceita uma mensagem customizada explicando o erro.
     *
     * @param message Mensagem descritiva do recurso em falta.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
