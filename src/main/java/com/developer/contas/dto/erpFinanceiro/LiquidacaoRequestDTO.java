package com.developer.contas.dto.erpFinanceiro;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class LiquidacaoRequestDTO {

    @NotNull(message = "O ID da conta bancária é obrigatório.")
    private Long contaBancariaId;

    @NotNull(message = "O valor pago é obrigatório.")
    @Positive(message = "O valor pago deve ser maior que zero.")
    private BigDecimal valorPago;
}
