package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.FieldValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

@Component
public class ServicosValidation extends GenericValidation<ServicoModel, IServicosRepository> implements IServicoValidation {

    @Override
    public void validateInsert(ServicoModel entity) {
        if (repository.existsByNome(entity.getNome())) {
            throw new RuleValidationException("Nome Duplicado", "Já existe um serviço cadastrado com esse nome.");
        }
    }

    @Override
    public void validateFields(ServicoModel entity) {
        super.validateFields(entity);
        if (entity.getNome() == null || entity.getNome().isBlank()) {
            throw new FieldValidationException("nome", "O nome do serviço é de preenchimento obrigatório.");
        }
        if (entity.getPreco() == null) {
            throw new FieldValidationException("preco", "O preço do serviço é de preenchimento obrigatório.");
        }
        if (entity.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new FieldValidationException("preco", "O preço não pode ser negativo.");
        }
    }
}