package br.edu.senai.fatesg.ads3.car_repair.business.mecanicos;

import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;
import org.springframework.stereotype.Component;

@Component
public class MecanicoValidation extends GenericValidation<MecanicoModel, IMecanicoRepository> implements IMecanicoValidation {
}
