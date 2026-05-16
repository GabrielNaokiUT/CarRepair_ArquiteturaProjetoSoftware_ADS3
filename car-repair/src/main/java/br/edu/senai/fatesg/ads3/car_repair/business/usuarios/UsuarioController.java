package br.edu.senai.fatesg.ads3.car_repair.business.usuarios;

import br.edu.senai.fatesg.ads3.car_repair.core.controllers.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends GenericController<UsuarioModel, UsuarioDTO, IUsuarioService, IUsuarioMapper> {
}
