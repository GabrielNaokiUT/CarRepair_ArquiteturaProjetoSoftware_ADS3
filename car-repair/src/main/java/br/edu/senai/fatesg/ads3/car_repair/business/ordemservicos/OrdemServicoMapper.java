/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.business.clientes.ClienteModel;
import br.edu.senai.fatesg.ads3.car_repair.business.servicos.ServicoModel;
import br.edu.senai.fatesg.ads3.car_repair.business.veiculos.VeiculoModel;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.GenericMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriel
 */
@Component

public class OrdemServicoMapper extends GenericMapper<OrdemServicoModel, OrdemServicoDTO> implements IOrdemServicoMapper {

    @Override
    public OrdemServicoDTO toDto(OrdemServicoModel entity) {
        if (entity == null) {
            return null;
        }
        OrdemServicoDTO dto = new OrdemServicoDTO();
        
        dto.setId(entity.getId());
        dto.setActive(entity.isAtivo());
        dto.setDescricaoProblema(entity.getDescricaoProblema());
        dto.setStatusOrdemServico(entity.getStatusOrdemServico());
        dto.setValorTotal(entity.getValorTotal());
        dto.setDataAbertura(entity.getDataAbertura());
        dto.setDataConclusao(entity.getDataConclusao());
        if (entity.getCliente() != null) {
            dto.setIdCliente(entity.getCliente().getId());
        }
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

        entity.setId(dto.getId());
        entity.setAtivo(dto.isActive());
        entity.setDescricaoProblema(dto.getDescricaoProblema());
        if (dto.getStatusOrdemServico() != null) {
            entity.setStatusOrdemServico(dto.getStatusOrdemServico());
        }
        entity.setValorTotal(dto.getValorTotal());
        entity.setDataAbertura(dto.getDataAbertura());
        entity.setDataConclusao(dto.getDataConclusao());
        if (dto.getIdCliente() != null) {
            ClienteModel cliente = new ClienteModel();
            cliente.setId(dto.getIdCliente());
            entity.setCliente(cliente);
        }
        if (dto.getIdVeiculo() != null) {
            VeiculoModel veiculo = new VeiculoModel();
            veiculo.setId(dto.getIdVeiculo());
            entity.setVeiculo(veiculo);
        }
        if (dto.getIdServico() != null) {
            ServicoModel servico = new ServicoModel();
            servico.setId(dto.getIdServico());
            entity.setServico(servico);
        }
        return entity;
    }

}
