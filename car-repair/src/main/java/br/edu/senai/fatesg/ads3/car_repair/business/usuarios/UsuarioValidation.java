package br.edu.senai.fatesg.ads3.car_repair.business.usuarios;

import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;
import org.springframework.stereotype.Component;

@Component
public class UsuarioValidation extends GenericValidation<UsuarioModel, IUsuarioRepository> implements IUsuarioValidation {
}
