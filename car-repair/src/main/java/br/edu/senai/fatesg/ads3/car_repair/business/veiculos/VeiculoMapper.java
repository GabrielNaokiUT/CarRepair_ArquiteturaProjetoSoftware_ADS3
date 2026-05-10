/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.business.clientes.ClienteModel;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.GenericMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriel
 */

@Component
public class VeiculoMapper extends GenericMapper<VeiculoModel, VeiculoDTO> implements IVeiculoMapper {

    @Override
    public VeiculoDTO toDto(VeiculoModel entity) {
        if (entity == null) {
            return null;
        }
        VeiculoDTO dto = new VeiculoDTO();
        dto.setId(entity.getId());
        dto.setActive(entity.isAtivo());
        dto.setPlaca(entity.getPlaca());
        dto.setMarca(entity.getMarca());
        dto.setModelo(entity.getModelo());
        dto.setAnoFabricacao(entity.getAnoFabricacao());
        dto.setCor(entity.getCor());
        dto.setQuilometragem(entity.getQuilometragem());
        if (entity.getCliente() != null) {
            dto.setIdCliente(entity.getCliente().getId());
        }
        return dto;
    }

    @Override
    public VeiculoModel toEntity(VeiculoDTO dto) {
        if (dto == null) {
            return null;
        }
        VeiculoModel entity = new VeiculoModel();
        entity.setId(dto.getId());
        entity.setAtivo(dto.isActive());
        entity.setPlaca(dto.getPlaca());
        entity.setMarca(dto.getMarca());
        entity.setModelo(dto.getModelo());
        entity.setAnoFabricacao(dto.getAnoFabricacao());
        entity.setCor(dto.getCor());
        entity.setQuilometragem(dto.getQuilometragem());
        if (dto.getIdCliente() != null) {
            ClienteModel cliente = new ClienteModel();
            cliente.setId(dto.getIdCliente());
            entity.setCliente(cliente);
        }
        return entity;
    }
}
