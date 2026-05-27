package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

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
public class VeiculoService extends GenericService
        <VeiculoModel, IVeiculoRepository, IVeiculoValidation> implements IVeiculoService {
    @Override
    protected void beforeInsert(VeiculoModel entity) {
        entity.setAtivo(true);
        entity.setDataHoraCriacao(new Date());
    }
}