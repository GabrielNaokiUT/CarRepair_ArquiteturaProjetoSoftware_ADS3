/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.FieldValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;

/**
 *
 * @author Gabriel
 */
public class ClienteValidation extends GenericValidation<ClienteModel, IClienteRepository> implements IClienteValidation {

    @Override
    public void validateFields(ClienteModel entity) {
        super.validateFields(entity);
        if (entity.getNome() == null || entity.getNome().isBlank()) {
            throw new FieldValidationException("nome", "O nome do cliente é de preenchimento obrigatório.");
        }
        if (entity.getCpf() == null || entity.getCpf().isBlank()) {
            throw new FieldValidationException("cpf", "O CPF do cliente é de preenchimento obrigatório.");
        }
        if (entity.getCpf().replaceAll("[^0-9]", "").length() != 11) {
            throw new FieldValidationException("cpf", "O CPF deve conter 11 dígitos numéricos.");
        }
    }

    @Override
    public void validateInsert(ClienteModel entity) {
        if (repository.existsByCpf(entity.getCpf())) {
            throw new RuleValidationException("CPF Duplicado", "Já existe um cliente cadastrado com esse CPF.");
        }
    }
}
