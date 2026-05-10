/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.business.clientes.ClienteModel;
import br.edu.senai.fatesg.ads3.car_repair.business.servicos.ServicoModel;
import br.edu.senai.fatesg.ads3.car_repair.business.veiculos.VeiculoModel;
import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Gabriel
 */
@Data
@Entity
@Table(name = "OrdemServico")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class OrdemServicoModel extends BaseModel {

    public enum StatusOrdemServico {
        ABERTA,
        EM_ANDAMENTO,
        CONCLUIDA,
        CANCELADA
    }

    @Column(name = "descricao_problema", length = 200, nullable = false)
    private String descricaoProblema;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusOrdemServico statusOrdemServico = StatusOrdemServico.ABERTA;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "data_abertura", nullable = false)
    private Date dataAbertura;

    @Column(name = "data_conclusao")
    private Date dataConclusao;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModel cliente;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private VeiculoModel veiculo;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private ServicoModel servico;
}
