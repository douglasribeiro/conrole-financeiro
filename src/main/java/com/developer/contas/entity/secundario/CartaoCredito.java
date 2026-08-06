package com.developer.contas.entity.secundario;


import java.math.BigDecimal;

import com.developer.contas.dto.CartaoCreditoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cartoes_credito", schema = "secundario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaoCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_bancaria_id", nullable = false)
    private ContaBancaria contaBancaria;

    @Column(name = "nome_cartao", nullable = false, length = 50)
    private String nomeCartao;

    @Column(length = 20)
    private String bandeira;

    @Column(name = "limite_total", nullable = false)
    private BigDecimal limiteTotal;

    @Column(name = "limite_disponivel", nullable = false)
    private BigDecimal limiteDisponivel;

    @Column(name = "dia_fechamento", nullable = false)
    private Integer diaFechamento;

    @Column(name = "dia_vencimento", nullable = false)
    private Integer diaVencimento;
    
    public CartaoCredito(CartaoCreditoDTO cartaoCreditoDTO) {
    	this.id = cartaoCreditoDTO.getId();
    	this.contaBancaria = cartaoCreditoDTO.getContaBancaria();
    	this.nomeCartao = cartaoCreditoDTO.getNomeCartao();
    	this.bandeira = cartaoCreditoDTO.getBandeira();
    	this.limiteTotal = cartaoCreditoDTO.getLimiteTotal();
    	this.limiteDisponivel = cartaoCreditoDTO.getLimiteDisponivel();
    	this.diaFechamento = cartaoCreditoDTO.getDiaFechamento();
    	this.diaVencimento = cartaoCreditoDTO.getDiaVencimento();
    }
}
