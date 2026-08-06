package com.developer.contas.controller;

import com.developer.contas.dto.LiquidacaoRequestDTO;
import com.developer.contas.service.CartaoCreditoService;
import com.developer.contas.service.LiquidacaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financeiro/lancamentos")
@RequiredArgsConstructor
public class LiquidacaoController {

    private final LiquidacaoService liquidacaoService;

    private final CartaoCreditoService cartaoCreditoService;

    /**
     * Endpoint para liquidar (pagar ou receber) uma fatura/título financeiro.
     * Rota: POST /api/financeiro/lancamentos/{id}/liquidar
     */
    @PostMapping("/{id}/liquidar")
    public ResponseEntity<String> liquidarFatura(
            @PathVariable Long id,
            @Valid @RequestBody LiquidacaoRequestDTO request) {

        try {
            // Invoca o Service Layer dentro de uma transação atômica
            liquidacaoService.liquidarFatura(id, request.getContaBancariaId(), request.getValorPago());

            return ResponseEntity.ok("Liquidação efetuada e integrada na contabilidade com sucesso!");

        } catch (IllegalArgumentException | IllegalStateException e) {
            // Trata erros de validação de negócio (ex: saldo insuficiente, título já pago)
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            // Trata erros inesperados do servidor
            return ResponseEntity.internalServerError().body("Erro interno ao processar a liquidação: " + e.getMessage());
        }
    }
    
    @PostMapping("/cartoes/forcar-fechamento")
    public ResponseEntity<String> forcarFechamento() {
        cartaoCreditoService.fecharFaturasAgendadas();
        return ResponseEntity.ok("Rotina de fechamento de faturas executada com sucesso!");
    }
}
