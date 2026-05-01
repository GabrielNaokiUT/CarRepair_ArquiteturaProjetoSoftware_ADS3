/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.business.servicos.IServicosRepository;
import br.edu.senai.fatesg.ads3.car_repair.business.veiculos.IVeiculoRepository;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.GenericMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriel
 */
@Component

public class OrdemServicoMapper extends GenericMapper<OrdemServicoModel, OrdemServicoDTO> implements IOrdemServicoMapper {

    @Autowired
    private IVeiculoRepository veiculoRepository;

    @Autowired
    private IServicosRepository servicoRepository;

    @Override
    public OrdemServicoDTO toDto(OrdemServicoModel entity) {
        if (entity == null) {
            return null;
        }
        OrdemServicoDTO dto = new OrdemServicoDTO();

        BeanUtils.copyProperties(entity, dto);

        dto.setActive(entity.isAtivo());

        if (entity.getVeiculo() != null) {
            dto.setIdVeiculo(entity.getVeiculo().getId());
        }

        if (entity.getServico() != null) {
            dto.setIdServico(entity.getServico().getId());
        }

        return dto;
    }

    @Override
    public OrdemServicoModel toEntity(OrdemServicoDTO dto) {
        if (dto == null) {
            return null;
        }
        OrdemServicoModel entity = new OrdemServicoModel();

        BeanUtils.copyProperties(dto, entity);

        // Mapeia o booleano e os relacionamentos
        entity.setAtivo(dto.isActive());

        // Mantém suas regras de valores padrão (Default values)
        if (entity.getValorTotal() == null) {
            entity.setValorTotal(BigDecimal.ZERO);
        }
        if (entity.getDataAbertura() == null) {
            entity.setDataAbertura(LocalDateTime.now());
        }

        if (dto.getIdVeiculo() != null) {
            entity.setVeiculo(veiculoRepository.getReferenceById(dto.getIdVeiculo()));
        }

        if (dto.getIdServico() != null) {
            entity.setServico(servicoRepository.getReferenceById(dto.getIdServico()));
        }

        return entity;
    }

}
