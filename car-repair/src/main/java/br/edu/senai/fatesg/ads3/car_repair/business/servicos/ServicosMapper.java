/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.helpers.GenericMapper;
import java.math.BigDecimal;
import org.springframework.beans.BeanUtils;
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

        BeanUtils.copyProperties(entity, dto);

        dto.setActive(entity.isAtivo());

        return dto;
    }

    @Override
    public ServicoModel toEntity(ServicosDTO dto) {
        if (dto == null) {
            return null;
        }
        ServicoModel entity = new ServicoModel();

        BeanUtils.copyProperties(dto, entity);

        entity.setAtivo(dto.isActive());

        if (dto.getPreco() == null) {
            entity.setPreco(BigDecimal.ZERO);
        }

        return entity;
    }

}
