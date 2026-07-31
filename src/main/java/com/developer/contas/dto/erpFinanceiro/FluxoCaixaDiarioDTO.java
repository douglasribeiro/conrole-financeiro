package com.developer.contas.dto.erpFinanceiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FluxoCaixaDiarioDTO {
    private LocalDate data;
    private BigDecimal entradasPrevistas;
    private BigDecimal saidasPrevistas;
    private BigDecimal entradasRealizadas;
    private BigDecimal saidasRealizadas;
    private BigDecimal saldoDiarioRealizado;
}
