/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriel
 */
@Service

public class OrdemServicoService extends GenericService<OrdemServicoModel, IOrdemServicoRepository, IOrdemServicoValidation> implements IOrdemServicoService {

    @Override
    protected void beforeInsert(OrdemServicoModel entity) {
        entity.setAtivo(true);
        entity.setDataHoraCriacao(new Date());
    }

}
