/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriel
 */
@Service
public class VeiculoService extends GenericService<VeiculoModel, IVeiculoRepository, IVeiculoValidation> implements IVeiculoService {

    @Override
    protected void beforeInsert(VeiculoModel entity) {
        entity.setAtivo(true);
        entity.setDataHoraCriacao(new Date());
    }
}
