/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Gabriel
 */

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor

public class VeiculoDTO extends BaseDTO {
    
    private String placa;
    private String marca;
    private String modelo;
    private Integer anoFabricacao;
    private String cor;
    private Integer quilometragem;
    private UUID clienteId;
    
}
