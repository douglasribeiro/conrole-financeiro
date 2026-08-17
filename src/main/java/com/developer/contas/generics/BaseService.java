package com.developer.contas.generics;

import java.util.List;
import java.util.stream.Collectors;

import com.developer.contas.dto.BancoDTO;
import com.developer.contas.entity.primario.Banco;
import com.developer.contas.repository.secundario.BancoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import com.developer.contas.exception.RecordNotFoundException;

// Adicionado o tipo ID genérico para o identificador da entidade
public abstract class BaseService<E, D, ID> {

    protected final JpaRepository<E, ID> repository;
    
    @Autowired
    protected ModelMapper mapper;

    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    // Construtor único que resolve a inicialização de todos os campos obrigatórios
    protected BaseService(BancoRepository repository, Class<Banco> entityClass, Class<BancoDTO> dtoClass) {
        this.repository = repository;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    public List<D> listar() {
        return repository.findAll().stream()
                .map(entity -> mapper.map(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public D salvar(D dto) {
        E entity = mapper.map(dto, entityClass);
        E savedEntity = repository.save(entity);
        return mapper.map(savedEntity, dtoClass);
    }

    public D findById(ID id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException((Long) id)); // Garante o cast se necessário
        return mapper.map(entity, dtoClass);
    }

    public void delete(ID id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException((Long) id));
        repository.delete(entity);
    }

    public D update(D dto) {
        return salvar(dto);
    }
}
