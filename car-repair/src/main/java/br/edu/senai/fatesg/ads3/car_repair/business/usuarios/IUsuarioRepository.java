package br.edu.senai.fatesg.ads3.car_repair.business.usuarios;

import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends IGenericRepository<UsuarioModel> {
}
