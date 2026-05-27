package br.edu.senai.fatesg.ads3.car_repair.business.mecanicos;

import br.edu.senai.fatesg.ads3.car_repair.core.controllers.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

@RestController
@RequestMapping("/mecanicos")
public class MecanicoController extends GenericController<MecanicoModel, MecanicoDTO, IMecanicoService, IMecanicoMapper> {

}