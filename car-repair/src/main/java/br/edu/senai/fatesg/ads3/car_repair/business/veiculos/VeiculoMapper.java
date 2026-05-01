/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.business.clientes.IClienteRepository;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.GenericMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriel
 */

@Component
public class VeiculoMapper extends GenericMapper<VeiculoModel, VeiculoDTO> implements IVeiculoMapper {

    @Autowired
    private IClienteRepository clienteRepository;
    
    @Override
    public VeiculoDTO toDto(VeiculoModel entity) {
        if (entity == null) return null;
        VeiculoDTO dto = new VeiculoDTO();
        
        BeanUtils.copyProperties(entity, dto);
        
        dto.setActive(entity.isAtivo());
        if (entity.getCliente() != null) {
            dto.setClienteId(entity.getCliente().getId());
        }
        return dto;
    }

    @Override
    public VeiculoModel toEntity(VeiculoDTO dto) {
        if (dto == null) return null;
        VeiculoModel entity = new VeiculoModel();
        
        BeanUtils.copyProperties(dto, entity);
        
        entity.setAtivo(dto.isActive());
        if (dto.getClienteId() != null) {
            entity.setCliente(clienteRepository.getReferenceById(dto.getClienteId()));
        }
        return entity;
    }
    
}
