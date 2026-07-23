package com.developer.contas.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.developer.contas.dto.MovimentacaoDTO;
import com.developer.contas.entity.Banco;
import com.developer.contas.entity.Movimentacao;
import com.developer.contas.exception.SaldoInsuficienteException;
import com.developer.contas.generics.BaseService;
import com.developer.contas.repository.BancoRepository;
import com.developer.contas.repository.MovimentacaoRepository;

import jakarta.transaction.Transactional;

@Service
public class MovimentacaoService extends BaseService<Movimentacao, MovimentacaoDTO> {

	private static final Logger log = LoggerFactory.getLogger(MovimentacaoService.class);
	
	private MovimentacaoRepository movimentacaoRepository;
	private BancoRepository bancoRepository;
	
	public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, BancoRepository bancoRepository) {
		super(Movimentacao.class, MovimentacaoDTO.class);
		this.movimentacaoRepository = movimentacaoRepository;
		this.bancoRepository = bancoRepository;
	}

	@Override
	@Transactional
	public MovimentacaoDTO salvar(MovimentacaoDTO dto) {
		log.info("Metodo personalizado de salvar novimentação");
		Optional<Movimentacao> movto =  movimentacaoRepository.buscarUltimoLancamento(dto.getBanco().getId());
		if(movto.isEmpty()) {
			dto.setSaldo(dto.getValor());
		}else {
			if(dto.getMovimento().getDescricao().equals("Credito")) {
				dto.setSaldo(movto.get().getSaldo().add(dto.getValor()) );
			} else {
				dto.setSaldo(movto.get().getSaldo().subtract(dto.getValor()) );
			}
			Optional<Banco> banco = bancoRepository.findById(dto.getBanco().getId());
			if(banco.isEmpty() || dto.getSaldo().compareTo(banco.get().getLimite().negate()) < 0) {
				throw new SaldoInsuficienteException("Banco não cadastrado ou Saldo insuficiente, limite de crédito excedido.");
			}
		}
		return super.salvar(dto);
	}

	
}
