package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

@Repository 
public interface IClienteRepository extends IGenericRepository <ClienteModel>{
    boolean existsByCpf(String cpf);
}