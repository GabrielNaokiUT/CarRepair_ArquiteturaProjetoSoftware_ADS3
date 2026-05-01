/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriel
 */
@Service

public class ServicosService extends GenericService<ServicoModel, IServicosRepository, IServicoValidation> implements IServicoService {

    @Override
    protected void beforeInsert(ServicoModel entity) {
        entity.setAtivo(true);
        entity.setDataHoraCriacao(new Date());
    }

}
