package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import java.math.BigDecimal;
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

public class ServicosDTO extends BaseDTO {    
    private UUID idServico;
    private String nome;
    private String descricao;
    private BigDecimal preco;
}