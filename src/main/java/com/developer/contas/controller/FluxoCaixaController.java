package com.developer.contas.controller;

import com.developer.contas.dto.FluxoCaixaDiarioDTO;
import com.developer.contas.service.FluxoCaixaService;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/financeiro/fluxo-caixa")
@RequiredArgsConstructor
public class FluxoCaixaController {

    private final FluxoCaixaService fluxoCaixaService;

    @GetMapping
    public ResponseEntity<List<FluxoCaixaDiarioDTO>> obterFluxoCaixa(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        List<FluxoCaixaDiarioDTO> dados = fluxoCaixaService.gerarFluxoCaixa(inicio, fim);
        return ResponseEntity.ok(dados);
    }
}
