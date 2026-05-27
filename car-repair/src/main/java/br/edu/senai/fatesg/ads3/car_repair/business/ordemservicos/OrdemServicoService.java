package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

@Service

public class OrdemServicoService extends GenericService
        <OrdemServicoModel, IOrdemServicoRepository, IOrdemServicoValidation> implements IOrdemServicoService {

    @Override
    protected void beforeInsert(OrdemServicoModel entity) {
        entity.setAtivo(true);
        entity.setDataHoraCriacao(new Date());
    }
}