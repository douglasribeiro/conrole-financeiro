package com.developer.contas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.developer.contas.entity.secundario.ContaBancaria;
import com.developer.contas.entity.secundario.LancamentoFinanceiro;
import com.developer.contas.entity.secundario.MovimentacaoBancaria;
import com.developer.contas.enuns.TipoMovimentacao;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoBancariaDTO {
	
    private Long id;
    private ContaBancaria contaBancaria;
    private LancamentoFinanceiro lancamentoFinanceiro;
    private TipoMovimentacao tipoMovimentacao;
    private BigDecimal valor;
    private BigDecimal saldo;
    private LocalDateTime dataMovimentacao = LocalDateTime.now();
    private String historico;
    
	public MovimentacaoBancariaDTO(MovimentacaoBancaria movimentacaoBancaria) {
		super();
		this.id = movimentacaoBancaria.getId();
		this.contaBancaria = movimentacaoBancaria.getContaBancaria();
		this.lancamentoFinanceiro = movimentacaoBancaria.getLancamentoFinanceiro();
		this.tipoMovimentacao = movimentacaoBancaria.getTipoMovimentacao();
		this.valor = movimentacaoBancaria.getValor();
		this.saldo = movimentacaoBancaria.getSaldo();
		this.dataMovimentacao = movimentacaoBancaria.getDataMovimentacao();
		this.historico = movimentacaoBancaria.getHistorico();
	}
    
    

}
