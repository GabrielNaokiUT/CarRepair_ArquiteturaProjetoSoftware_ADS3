package br.edu.senai.fatesg.ads3.car_repair.business.usuarios;

import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

@Repository
public interface IUsuarioRepository extends IGenericRepository<UsuarioModel> {
}