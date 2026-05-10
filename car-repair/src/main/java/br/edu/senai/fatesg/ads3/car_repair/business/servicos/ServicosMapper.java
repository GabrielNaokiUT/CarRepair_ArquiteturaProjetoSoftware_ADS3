/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.helpers.GenericMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriel
 */
@Component

public class ServicosMapper extends GenericMapper<ServicoModel, ServicosDTO> implements IServicoMapper {

    @Override
    public ServicosDTO toDto(ServicoModel entity) {
        if (entity == null) {
            return null;
        }
        ServicosDTO dto = new ServicosDTO();

        dto.setId(entity.getId());
        dto.setActive(entity.isAtivo());
        dto.setNome(entity.getNome());
        dto.setDescricao(entity.getDescricao());
        dto.setPreco(entity.getPreco());
        return dto;
    }

    @Override
    public ServicoModel toEntity(ServicosDTO dto) {
        if (dto == null) {
            return null;
        }
        ServicoModel entity = new ServicoModel();

        entity.setId(dto.getId());
        entity.setAtivo(dto.isActive());
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setPreco(dto.getPreco());
        return entity;
    }

}
