package br.edu.senai.fatesg.ads3.car_repair.business.mecanicos;

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
public class MecanicoDTO extends BaseDTO {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String especialidade;
    private String crea;
}