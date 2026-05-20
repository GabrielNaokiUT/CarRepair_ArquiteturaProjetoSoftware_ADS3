package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.business.clientes.ClienteModel;
import br.edu.senai.fatesg.ads3.car_repair.business.mecanicos.MecanicoModel;
import br.edu.senai.fatesg.ads3.car_repair.business.pecas.PecaModel;
import br.edu.senai.fatesg.ads3.car_repair.business.servicos.ServicoModel;
import br.edu.senai.fatesg.ads3.car_repair.business.usuarios.UsuarioModel;
import br.edu.senai.fatesg.ads3.car_repair.business.veiculos.VeiculoModel;
import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @JoinColumn(name = "mecanico_responsavel_id")
    private MecanicoModel mecanicoResponsavel;

    @ManyToOne
    @JoinColumn(name = "usuario_responsavel_id")
    private UsuarioModel usuarioResponsavel;

    @ManyToMany
    @JoinTable(
        name = "ordem_servico_servicos",
        joinColumns = @JoinColumn(name = "ordem_servico_id"),
        inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<ServicoModel> servicosExecutados = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "ordem_servico_pecas",
        joinColumns = @JoinColumn(name = "ordem_servico_id"),
        inverseJoinColumns = @JoinColumn(name = "peca_id")
    )
    private List<PecaModel> pecasAplicadas = new ArrayList<>();
}
