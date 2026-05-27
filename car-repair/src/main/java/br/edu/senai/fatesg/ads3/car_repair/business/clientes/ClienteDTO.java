package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import java.util.UUID;
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

public class ClienteDTO extends BaseDTO {
    private UUID idCliente;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}