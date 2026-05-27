package br.edu.senai.fatesg.ads3.car_repair.core.controllers;


import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;
import br.edu.senai.fatesg.ads3.car_repair.core.services.IGenericService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * E: Entity (Entidade de Banco)
 * D: DTO (Data Transfer Object)
 * S: Service especializado
 * M: Mapper especializado
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 * @param <E>
 * @param <D>
 * @param <S>
 * @param <M>
 */

public abstract class GenericController<
    E extends BaseModel, 
    D extends BaseDTO, 
    S extends IGenericService<E, ?, ?>, 
    M extends IGenericMapper<E, D>> {

    @Autowired
    protected S service;

    @Autowired
    protected M mapper;

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable UUID id) {
        E entity = service.findByIdActive(id);
        D dto = mapper.toDto(entity);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<D>> findAll(Pageable pageable) {
        
        int size = Math.min(pageable.getPageSize(), 50);
        Pageable paginacaoSegura = PageRequest.of(pageable.getPageNumber(), size);
        
        
        Page<E> entities = service.findAllActive(paginacaoSegura);
        Page<D> dtos = mapper.toDtoPage(entities);
        return ResponseEntity.ok(dtos);
    }
    
    // Endpoint novo: GET /clientes/todos, GET /veiculos/todos, etc.
    // Retorna lista simples sem paginação — necessário para o frontend Angular funcionar corretamente
    @GetMapping("/todos")
    public ResponseEntity<List<D>> findAllList() {
        // Busca todas as entidades ativas sem paginação
        List<E> entities = service.findAllActiveList();

        // Converte cada entidade para DTO antes de enviar para o frontend
        // O mapper já existia no projeto — só estamos reutilizando ele aqui
        List<D> dtos = entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<D> insert(@RequestBody D dto) {
        E entity = mapper.toEntity(dto);
        E saved = service.insert(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@PathVariable UUID id, @RequestBody D dto) {
        E entity = mapper.toEntity(dto);
        entity.setId(id); // Garante que o ID da URL seja o ID processado
        entity.setAtivo(true);
        E updated = service.update(entity);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok("Registro removido com sucesso.");
    }
}