package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.business.clientes.ClienteModel;
import br.edu.senai.fatesg.ads3.car_repair.business.mecanicos.MecanicoModel;
import br.edu.senai.fatesg.ads3.car_repair.business.pecas.PecaModel;
import br.edu.senai.fatesg.ads3.car_repair.business.servicos.ServicoModel;
import br.edu.senai.fatesg.ads3.car_repair.business.usuarios.UsuarioModel;
import br.edu.senai.fatesg.ads3.car_repair.business.veiculos.VeiculoModel;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.GenericMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OrdemServicoMapper extends GenericMapper<OrdemServicoModel, OrdemServicoDTO> implements IOrdemServicoMapper {

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

        if (dto.getIdCliente() != null) {
            ClienteModel c = new ClienteModel();
            c.setId(dto.getIdCliente());
            entity.setCliente(c);
        }

        if (dto.getIdVeiculo() != null) {
            VeiculoModel v = new VeiculoModel();
            v.setId(dto.getIdVeiculo());
            entity.setVeiculo(v);
        }

        if (dto.getIdMecanicoResponsavel() != null) {
            MecanicoModel m = new MecanicoModel();
            m.setId(dto.getIdMecanicoResponsavel());
            entity.setMecanicoResponsavel(m);
        }

        if (dto.getIdUsuarioResponsavel() != null) {
            UsuarioModel u = new UsuarioModel();
            u.setId(dto.getIdUsuarioResponsavel());
            entity.setUsuarioResponsavel(u);
        }

        if (dto.getIdServicosExecutados() != null) {
            List<ServicoModel> servicos = new ArrayList<>();
            for (UUID id : dto.getIdServicosExecutados()) {
                ServicoModel s = new ServicoModel();
                s.setId(id);
                servicos.add(s);
            }
            entity.setServicosExecutados(servicos);
        }

        if (dto.getIdPecasAplicadas() != null) {
            List<PecaModel> pecas = new ArrayList<>();
            for (UUID id : dto.getIdPecasAplicadas()) {
                PecaModel p = new PecaModel();
                p.setId(id);
                pecas.add(p);
            }
            entity.setPecasAplicadas(pecas);
        }

        return entity;
    }
}
