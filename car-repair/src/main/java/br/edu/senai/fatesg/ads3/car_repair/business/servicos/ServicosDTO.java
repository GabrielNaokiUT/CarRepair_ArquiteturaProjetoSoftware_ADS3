/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *
 * @author Gabriel
 */

@Data
@EqualsAndHashCode(callSuper = true)

public class ServicosDTO extends BaseDTO {
    
    private UUID idServico;
    private String nome;
    private String descricao;
    private BigDecimal preco;
}
