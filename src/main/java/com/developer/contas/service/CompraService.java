package com.developer.contas.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.developer.contas.dto.CompraDTO;
import com.developer.contas.entity.Cartao;
import com.developer.contas.entity.Compra;
import com.developer.contas.entity.Transacao;
import com.developer.contas.enuns.FormaPagamento;
import com.developer.contas.generics.BaseService;
import com.developer.contas.repository.TransacaoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class CompraService extends BaseService<Compra, CompraDTO> {

	private static final Logger log = LoggerFactory.getLogger(CompraService.class);

	private TransacaoRepository transacaoRepository;

	@PersistenceContext
	private EntityManager entityManager;
	 
	public CompraService(TransacaoRepository transacaoRepository, EntityManager entityManager) {
		super(Compra.class, CompraDTO.class);
		this.transacaoRepository = transacaoRepository;
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public CompraDTO salvar(CompraDTO dto) {
		
		log.info("Implemantção personalizada do service compra.");
		
		if (dto.getFormaPagamento().equals(FormaPagamento.CREDITO) ||
				dto.getFormaPagamento().equals(FormaPagamento.AVISTA) ||
				dto.getFormaPagamento().equals(FormaPagamento.PIX) ||
				dto.getFormaPagamento().equals(FormaPagamento.DEBITO)) {
				dto.setParcelas(1);
				dto.setCartao(null);
				dto.setVencimento(LocalDate.now());
			return super.salvar(dto);
		}
		Compra compra = new Compra(super.salvar(dto));
		entityManager.flush();
		if (dto.getFormaPagamento() == FormaPagamento.CREDITOPARCELADO) {
			Cartao cartao = consultaCartao(dto.getCartao());
	        int totalParcelas = dto.getParcelas();
	        BigDecimal valorParcela = dto.getValor().divide(BigDecimal.valueOf(totalParcelas), 2, RoundingMode.HALF_UP);
	        
	        IntStream.rangeClosed(1, totalParcelas).forEach(i -> {
	            CompraDTO parcelaDto = new CompraDTO();
	            parcelaDto.setValor(valorParcela);
	            parcelaDto.setParcelas(i);
	            parcelaDto.setDescricao(dto.getDescricao());
	            parcelaDto.setFormaPagamento(dto.getFormaPagamento());
	            parcelaDto.setValor(dto.getValor());
	            parcelaDto.setVencimento(dto.getVencimento());
	            
	            Transacao transacao = Transacao.builder()
	                .compra(compra)
	                .dtTransacao(LocalDate.now())
	                .valor(valorParcela)
	                .parcelas(i+"/"+dto.getParcelas())
	                .build();
	                
	            transacaoRepository.save(transacao);
	        });
	        
	    }
		
		return null; //super.salvar(dto);
	}
	
	private Cartao consultaCartao(Cartao objIn) {
		Cartao cartao = entityManager.find(Cartao.class, objIn.getId());
		if(Integer.parseInt(cartao.getFechamento()) < LocalDate.now().getDayOfMonth() && 
		   Integer.parseInt(cartao.getMesCorrente()) < LocalDate.now().getMonthValue()) {
			cartao.setMesCorrente(String.valueOf(LocalDate.now().getMonthValue()));
			return entityManager.merge(cartao);
		}
        return objIn;
	}

}
