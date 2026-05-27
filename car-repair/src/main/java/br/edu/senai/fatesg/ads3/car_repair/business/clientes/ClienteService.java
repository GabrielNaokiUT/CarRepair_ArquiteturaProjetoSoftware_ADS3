package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

@Service
public class ClienteService extends GenericService <ClienteModel, IClienteRepository, IClienteValidation> implements IClienteService {

    @Override
    protected void beforeInsert(ClienteModel entity) {
        entity.setAtivo(true);
        entity.setDataHoraCriacao(new Date());
    }  
}