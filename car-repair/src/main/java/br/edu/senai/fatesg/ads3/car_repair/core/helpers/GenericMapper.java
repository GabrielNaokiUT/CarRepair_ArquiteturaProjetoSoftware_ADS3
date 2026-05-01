/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.core.helpers;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 *
 * @author Gabriel
 * @param <E>
 * @param <D>
 */

public abstract class GenericMapper <E extends BaseModel, D extends BaseDTO>
        implements IGenericMapper<E, D>{

    @Override
    public abstract D toDto(E entity);
    
    @Override
    public abstract E toEntity(D dto);

    @Override
    public Page<D> toDtoPage(Page<E> page) {
        if (page == null) return null;
            return page.map(this::toDto);
    }

    @Override
    public List<D> toDtoList(List<E> entities) {
        if (entities == null) return null; // 
            return entities.stream().map(this::toDto).collect(Collectors.toList());
    }   
}
