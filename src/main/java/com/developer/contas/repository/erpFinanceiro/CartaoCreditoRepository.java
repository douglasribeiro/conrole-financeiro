package com.developer.contas.repository.erpFinanceiro;
import com.developer.contas.entity.erpFinanceiro.CartaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {
    // Procura todos os cartões que fecham num dia específico do mês
    List<CartaoCredito> findByDiaFechamento(Integer dia);
}
