/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.business.ordemservicos;

import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gabriel
 */
@Repository

public interface IOrdemServicoRepository extends IGenericRepository<OrdemServicoModel> {

    boolean existsByNumero(String numero);
}
