package com.developer.contas.entity.secundario;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.developer.contas.enuns.OrigemLote;

@Entity
@Table(name = "lotes_contabeis", schema = "secundario")
@Data
public class LoteContabil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_fato", nullable = false)
    private LocalDate dataFato;

    @Column(length = 200)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private OrigemLote origem;

    @OneToMany(mappedBy = "loteContabil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LancamentoContabil> linhas = new ArrayList<>();

    // Método utilitário para adicionar linhas garantindo a consistência bidirecional
    public void adicionarLinha(LancamentoContabil linha) {
        linhas.add(linha);
        linha.setLoteContabil(this);
    }
}
