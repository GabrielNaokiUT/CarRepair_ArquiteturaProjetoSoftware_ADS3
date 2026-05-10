/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *
 * @author Gabriel
 */

@Data
@EqualsAndHashCode(callSuper = true)

public class VeiculoDTO extends BaseDTO {
    
    private UUID idVeiculo;
    private String placa;
    private String marca;
    private String modelo;
    private Integer anoFabricacao;
    private String cor;
    private Integer quilometragem;
    private UUID idCliente;
    
}
