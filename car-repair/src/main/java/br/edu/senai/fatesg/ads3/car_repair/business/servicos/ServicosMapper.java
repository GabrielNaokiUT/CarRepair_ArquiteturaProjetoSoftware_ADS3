package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.helpers.GenericMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
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