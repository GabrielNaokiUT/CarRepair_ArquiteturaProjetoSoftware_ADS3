/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.FieldValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriel
 */
@Component

public class VeiculoValidation extends GenericValidation<VeiculoModel, IVeiculoRepository> implements IVeiculoValidation {

    @Override
    public void validateInsert(VeiculoModel entity) {
        if (repository.existsByPlaca(entity.getPlaca())) {
            throw new RuleValidationException("Placa Duplicada", "Já existe um veículo com essa placa cadastrado.");
        }
    }

    @Override
    public void validateFields(VeiculoModel entity) {
        if (entity.getPlaca() == null || entity.getPlaca().isBlank()) {
            throw new FieldValidationException("placa", "A placa do veículo é de preenchimento obrigatório.");
        }

        if (entity.getMarca() == null || entity.getMarca().isBlank()) {
            throw new FieldValidationException("marca", "A marca do veículo é de preenchimento obrigatório.");
        }

        if (entity.getModelo() == null || entity.getModelo().isBlank()) {
            throw new FieldValidationException("modelo", "O modelo do veículo é de preenchimento obrigatório.");
        }

        if (entity.getAnoFabricacao() == null) {
            throw new FieldValidationException("anoFabricacao", "O ano de fabricação é de preenchimento obrigatório.");
        }

        if (entity.getCliente() == null || entity.getCliente().getId() == null) {
            throw new FieldValidationException("clienteId", "O cliente do veículo é de preenchimento obrigatório.");
        }
    }

}
