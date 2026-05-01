/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 *
 * @author Gabriel
 */

@Service
public class ClienteService extends GenericService <ClienteModel, IClienteRepository, IClienteValidation> implements IClienteService {

    @Override
    protected void beforeInsert(ClienteModel entity) {
        entity.setAtivo(true);
        entity.setDataHoraCriacao(new Date());
    }  
}
