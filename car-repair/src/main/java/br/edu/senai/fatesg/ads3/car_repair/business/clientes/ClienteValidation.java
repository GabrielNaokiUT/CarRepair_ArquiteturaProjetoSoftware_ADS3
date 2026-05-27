/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.FieldValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriel
 */

@Component
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
   
        String cpfCompleto = entity.getCpf().replaceAll("[^0-9]", "");
        if(cpfCompleto.chars().distinct().count() == 1){
            throw new FieldValidationException("cpf", "CPF inválido.");
        }
        if(!validarAlgoritmoCPF(cpfCompleto)){
            throw new FieldValidationException("cpf", "CPF inválido.");  
        }
        
        //Email
        if(entity.getEmail() != null && !entity.getEmail().isBlank()){
            if(!entity.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")){
                throw new FieldValidationException("email", "O e-mail informado não é válido.");
            }
        }
     }    

    @Override
    public void validateInsert(ClienteModel entity) {
        if (repository.existsByCpf(entity.getCpf())) {
            throw new RuleValidationException("CPF Duplicado", "Já existe um cliente cadastrado com esse CPF.");
        }
    }

    private boolean validarAlgoritmoCPF(String cpfCompleto) { //Algoritmo oficial de validação do CPF
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpfCompleto.charAt(i) - '0') * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) {
            primeiroDigito = 0;
        }
        if (primeiroDigito != (cpfCompleto.charAt(9) - '0')) {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpfCompleto.charAt(i) - '0') * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) {
            segundoDigito = 0;
        }
        return segundoDigito == (cpfCompleto.charAt(10) - '0');
    }
}
