/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos.OrdemServicoModel.StatusOrdem;
import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

public class OrdemServicoDTO extends BaseDTO{

    private String numero;
    private String descricaoProblema;
    private String diagnostico;
    private StatusOrdem status;
    private BigDecimal valorTotal;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataConclusao;
    private UUID idVeiculo;
    private UUID idServico;
}
