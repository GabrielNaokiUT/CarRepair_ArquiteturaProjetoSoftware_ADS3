/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.helpers.GenericMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriel
 */

@Component
public class ClienteMapper extends GenericMapper <ClienteModel, ClienteDTO> implements IClienteMapper{

    @Override
    public ClienteDTO toDto(ClienteModel entity) {
        if (entity == null) {
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        dto.setId(entity.getId());
        dto.setActive(entity.isAtivo());
        dto.setNome(entity.getNome());
        dto.setCpf(entity.getCpf());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());
        dto.setEndereco(entity.getEndereco());
        dto.setBairro(entity.getBairro());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        dto.setCep(entity.getCep());
        return dto;
    }

    @Override
    public ClienteModel toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }
        ClienteModel entity = new ClienteModel();
        entity.setId(dto.getId());
        entity.setAtivo(dto.isActive());
        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setEndereco(dto.getEndereco());
        entity.setBairro(dto.getBairro());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setCep(dto.getCep());
        return entity;
    }   
}
