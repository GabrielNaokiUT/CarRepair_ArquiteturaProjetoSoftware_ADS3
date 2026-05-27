package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrdemServicoDTO extends BaseDTO {

    private String descricaoProblema;
    private OrdemServicoModel.StatusOrdemServico statusOrdemServico;
    private BigDecimal valorTotal;
    private Date dataAbertura;
    private Date dataConclusao;
    private UUID idCliente;
    private UUID idVeiculo;
    private UUID idMecanicoResponsavel;
    private UUID idUsuarioResponsavel;
    private List<UUID> idServicosExecutados;
    private List<UUID> idPecasAplicadas;
}
