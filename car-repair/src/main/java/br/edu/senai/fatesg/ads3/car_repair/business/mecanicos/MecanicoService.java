package br.edu.senai.fatesg.ads3.car_repair.business.mecanicos;

import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class MecanicoService extends GenericService<MecanicoModel, IMecanicoRepository, IMecanicoValidation> implements IMecanicoService {

    @Override
    protected void beforeInsert(MecanicoModel entity) {
        entity.setAtivo(true);
        entity.setDataHoraCriacao(new Date());
    }
}
