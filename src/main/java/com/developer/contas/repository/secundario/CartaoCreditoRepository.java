package com.developer.contas.repository.secundario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developer.contas.entity.secundario.CartaoCredito;

import java.util.List;

@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {
    // Procura todos os cartões que fecham num dia específico do mês
    List<CartaoCredito> findByDiaFechamento(Integer dia);
}
