package br.edu.senai.fatesg.ads3.car_repair.business.usuarios;

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
public class UsuarioService extends GenericService<UsuarioModel, IUsuarioRepository, IUsuarioValidation> implements IUsuarioService {

    @Override
    protected void beforeInsert(UsuarioModel entity) {
        entity.setAtivo(true);
        entity.setDataHoraCriacao(new Date());
    }
}