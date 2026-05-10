/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.business.clientes.ClienteModel;
import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Gabriel
 */

@Data
@Entity
@Table(name = "Veiculo")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class VeiculoModel extends BaseModel {

    @Column(name = "placa", length = 7, unique = true)
    private String placa;

    @Column(name = "marca", length = 50, nullable = false)
    private String marca;

    @Column(name = "modelo", length = 50, nullable = false)
    private String modelo;

    @Column(name = "ano_fabricacao", nullable = false)
    private Integer anoFabricacao;

    @Column(name = "cor", length = 20, nullable = false)
    private String cor;

    @Column(name = "quilometragem")
    private Integer quilometragem;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModel cliente;
}

