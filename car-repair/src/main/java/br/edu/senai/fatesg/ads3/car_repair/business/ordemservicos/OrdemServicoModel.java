/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.business.servicos.ServicoModel;
import br.edu.senai.fatesg.ads3.car_repair.business.veiculos.VeiculoModel;
import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

public class OrdemServicoModel extends BaseModel {

    public enum StatusOrdem {
        Aberta,
        emAndamento,
        Concluida,
        Cancelada
    }

    @Column(name = "numero", nullable = false, unique = true, length = 20)
    private String numero;

    @Column(name = "descricaoProblema", nullable = false, columnDefinition = "TEXT")
    private String descricaoProblema;

    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusOrdem status = StatusOrdem.Aberta;

    @Column(name = "valorTotal", precision = 10, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(name = "dataAbertura", nullable = false)
    private LocalDateTime dataAbertura = LocalDateTime.now();

    @Column(name = "dataConclusao")
    private LocalDateTime dataConclusao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "isVeiculo", nullable = false)
    private VeiculoModel veiculo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idServico", nullable = false)
    private ServicoModel servico;

    public void iniciar() {
        if (this.status != StatusOrdem.Aberta) {
            throw new IllegalStateException("Apenas ordens de serviços abertas podem ser iniciadas.");
        }
        this.status = StatusOrdem.emAndamento;
    }

    public void concluir() {
        if (this.status == StatusOrdem.Cancelada || this.status == StatusOrdem.Concluida) {
            throw new IllegalStateException("Esta ordem de serviço já foi finalizada ou cancelada.");
        }
        this.status = StatusOrdem.Concluida;
        this.dataConclusao = LocalDateTime.now();
    }

    public void cancelar() {
        if (this.status == StatusOrdem.Concluida) {
            throw new IllegalStateException("Não é possível cancelar uma ordem de serviço já concluída.");
        }
        this.status = StatusOrdem.Cancelada;
    }
}
