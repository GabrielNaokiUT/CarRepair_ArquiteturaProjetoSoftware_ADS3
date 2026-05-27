package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.business.clientes.ClienteModel;
import br.edu.senai.fatesg.ads3.car_repair.business.mecanicos.MecanicoModel;
import br.edu.senai.fatesg.ads3.car_repair.business.pecas.PecaModel;
import br.edu.senai.fatesg.ads3.car_repair.business.servicos.ServicoModel;
import br.edu.senai.fatesg.ads3.car_repair.business.usuarios.UsuarioModel;
import br.edu.senai.fatesg.ads3.car_repair.business.veiculos.VeiculoModel;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.GenericMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

@Component
public class OrdemServicoMapper extends GenericMapper<OrdemServicoModel, OrdemServicoDTO> implements IOrdemServicoMapper {

    @PersistenceContext
    private EntityManager em;

    @Override
    public OrdemServicoDTO toDto(OrdemServicoModel entity) {
        if (entity == null) return null;

        OrdemServicoDTO dto = new OrdemServicoDTO();
        dto.setId(entity.getId());
        dto.setActive(entity.isAtivo());
        dto.setDescricaoProblema(entity.getDescricaoProblema());
        dto.setStatusOrdemServico(entity.getStatusOrdemServico());
        dto.setValorTotal(entity.getValorTotal());
        dto.setDataAbertura(entity.getDataAbertura());
        dto.setDataConclusao(entity.getDataConclusao());

        if (entity.getCliente() != null)
            dto.setIdCliente(entity.getCliente().getId());

        if (entity.getVeiculo() != null)
            dto.setIdVeiculo(entity.getVeiculo().getId());

        if (entity.getMecanicoResponsavel() != null)
            dto.setIdMecanicoResponsavel(entity.getMecanicoResponsavel().getId());

        if (entity.getUsuarioResponsavel() != null)
            dto.setIdUsuarioResponsavel(entity.getUsuarioResponsavel().getId());

        if (entity.getServicosExecutados() != null) {
            List<UUID> ids = new ArrayList<>();
            for (ServicoModel s : entity.getServicosExecutados()) ids.add(s.getId());
            dto.setIdServicosExecutados(ids);
        }

        if (entity.getPecasAplicadas() != null) {
            List<UUID> ids = new ArrayList<>();
            for (PecaModel p : entity.getPecasAplicadas()) ids.add(p.getId());
            dto.setIdPecasAplicadas(ids);
        }

        return dto;
    }

    @Override
    public OrdemServicoModel toEntity(OrdemServicoDTO dto) {
        if (dto == null) return null;

        OrdemServicoModel entity = new OrdemServicoModel();
        entity.setId(dto.getId());
        entity.setAtivo(dto.isActive());
        entity.setDescricaoProblema(dto.getDescricaoProblema());
        if (dto.getStatusOrdemServico() != null)
            entity.setStatusOrdemServico(dto.getStatusOrdemServico());
        entity.setValorTotal(dto.getValorTotal());
        entity.setDataAbertura(dto.getDataAbertura());
        entity.setDataConclusao(dto.getDataConclusao());

        if (dto.getIdCliente() != null)
            entity.setCliente(em.getReference(ClienteModel.class, dto.getIdCliente()));

        if (dto.getIdVeiculo() != null)
            entity.setVeiculo(em.getReference(VeiculoModel.class, dto.getIdVeiculo()));

        if (dto.getIdMecanicoResponsavel() != null)
            entity.setMecanicoResponsavel(em.getReference(MecanicoModel.class, dto.getIdMecanicoResponsavel()));

        if (dto.getIdUsuarioResponsavel() != null)
            entity.setUsuarioResponsavel(em.getReference(UsuarioModel.class, dto.getIdUsuarioResponsavel()));

        if (dto.getIdServicosExecutados() != null) {
            List<ServicoModel> servicos = new ArrayList<>();
            for (UUID id : dto.getIdServicosExecutados())
                servicos.add(em.getReference(ServicoModel.class, id));
            entity.setServicosExecutados(servicos);
        }

        if (dto.getIdPecasAplicadas() != null) {
            List<PecaModel> pecas = new ArrayList<>();
            for (UUID id : dto.getIdPecasAplicadas())
                pecas.add(em.getReference(PecaModel.class, id));
            entity.setPecasAplicadas(pecas);
        }

        return entity;
    }
}