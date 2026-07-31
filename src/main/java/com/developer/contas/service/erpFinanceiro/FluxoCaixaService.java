package com.developer.contas.service.erpFinanceiro;

import com.developer.contas.dto.erpFinanceiro.FluxoCaixaDiarioDTO;
import com.developer.contas.repository.erpFinanceiro.LancamentoFinanceiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FluxoCaixaService {

    private final LancamentoFinanceiroRepository repository;

    public List<FluxoCaixaDiarioDTO> gerarFluxoCaixa(LocalDate inicio, LocalDate fim) {
        // Executa as consultas na base de dados
        List<Object[]> previstosRaw = repository.buscarProjecaoPrevista(inicio, fim);
        List<Object[]> realizadosRaw = repository.buscarMovimentacaoRealizada(inicio, fim);

        // Mapas auxiliares para busca rápida por Data
        Map<LocalDate, BigDecimal[]> previstoMap = transformarParaMap(previstosRaw);
        Map<LocalDate, BigDecimal[]> realizadoMap = transformarParaMap(realizadosRaw);

        List<FluxoCaixaDiarioDTO> fluxoCompleto = new ArrayList<>();

        // Itera dia a dia do período para montar a linha do tempo completa sem buracos de datas
        for (LocalDate data = inicio; !data.isAfter(fim); data = data.plusDays(1)) {
            BigDecimal[] prev = previstoMap.getOrDefault(data, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            BigDecimal[] real = realizadoMap.getOrDefault(data, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});

            BigDecimal entradasPrevistas = prev[0];
            BigDecimal saidasPrevistas = prev[1];
            BigDecimal entradasRealizadas = real[0];
            BigDecimal saidasRealizadas = real[1];

            // Saldo diário líquido do que de fato aconteceu no caixa
            BigDecimal saldoDiarioRealizado = entradasRealizadas.subtract(saidasRealizadas);

            fluxoCompleto.add(new FluxoCaixaDiarioDTO(
                    data,
                    entradasPrevistas,
                    saidasPrevistas,
                    entradasRealizadas,
                    saidasRealizadas,
                    saldoDiarioRealizado
            ));
        }

        return fluxoCompleto;
    }

    private Map<LocalDate, BigDecimal[]> transformarParaMap(List<Object[]> rawList) {
        Map<LocalDate, BigDecimal[]> map = new HashMap<>();
        for (Object[] row : rawList) {
            // Garante a conversão correta do tipo Date do SQL para LocalDate
            LocalDate data = (row[0] instanceof Date) ? ((Date) row[0]).toLocalDate() : (LocalDate) row[0];
            BigDecimal entradas = BigDecimal.valueOf(((Number) row[1]).doubleValue());
            BigDecimal saidas = BigDecimal.valueOf(((Number) row[2]).doubleValue());
            map.put(data, new BigDecimal[]{entradas, saidas});
        }
        return map;
    }
}
