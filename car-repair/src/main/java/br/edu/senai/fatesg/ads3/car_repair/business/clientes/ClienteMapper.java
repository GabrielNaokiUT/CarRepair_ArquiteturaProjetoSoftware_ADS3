/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.helpers.GenericMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriel
 */

@Component
public class ClienteMapper extends GenericMapper <ClienteModel, ClienteDTO> implements IClienteMapper{

    @Override
    public ClienteDTO toDto(ClienteModel entity) {
        if (entity == null) return null;
        ClienteDTO dto = new ClienteDTO();
        
        BeanUtils.copyProperties(entity, dto);
        
        dto.setActive(entity.isAtivo());
        dto.setCep(entity.getCEP()); 
        
        return dto;
    }

    @Override
    public ClienteModel toEntity(ClienteDTO dto) {
        if (dto == null) return null;
        ClienteModel entity = new ClienteModel();
        
        BeanUtils.copyProperties(dto, entity);
        
        entity.setAtivo(dto.isActive());
        entity.setCEP(dto.getCep());
        
        return entity;
    }    
}
