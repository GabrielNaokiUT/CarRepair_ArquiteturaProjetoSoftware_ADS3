package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

@Repository

public interface IServicosRepository extends IGenericRepository <ServicoModel>  {
    boolean existsByNome(String Nome);
}