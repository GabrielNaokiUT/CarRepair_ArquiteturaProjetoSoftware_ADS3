package br.edu.senai.fatesg.ads3.car_repair.business.usuarios;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class UsuarioDTO extends BaseDTO {
    private String nome;
    private String login;
    private String email;
    private String perfil;
}