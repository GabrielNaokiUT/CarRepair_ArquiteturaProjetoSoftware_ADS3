/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.FieldValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriel
 */
@Component

public class OrdemServicoValidation extends GenericValidation<OrdemServicoModel, IOrdemServicoRepository> implements IOrdemServicoValidation {

    @Override
    public void validateInsert(OrdemServicoModel entity) {
        if (repository.existsByNumero(entity.getNumero())) {
            throw new RuleValidationException("Número Duplicado", "Já existe uma ordem de serviço com esse número.");
        }
    }

    @Override
    public void validateFields(OrdemServicoModel entity) {
        super.validateFields(entity);
        if (entity.getNumero() == null || entity.getNumero().isBlank()) {
            throw new FieldValidationException("numero", "O número da ordem de serviço é de preenchimento obrigatório.");
        }
        if (entity.getDescricaoProblema() == null || entity.getDescricaoProblema().isBlank()) {
            throw new FieldValidationException("descricaoProblema", "A descrição do problema é de preenchimento obrigatória.");
        }
        if (entity.getVeiculo() == null || entity.getVeiculo().getId() == null) {
            throw new FieldValidationException("veiculoId", "O veículo da ordem de serviço é de preenchimento obrigatório.");
        }
        if (entity.getServico() == null || entity.getServico().getId() == null) {
            throw new FieldValidationException("servicoId", "O serviço da ordem é de preenchimento obrigatório.");
        }
    }

}
