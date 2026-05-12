package br.edu.senai.fatesg.ads3.car_repair.core.services;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.IGenericValidation;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Caio4breu
 */


public interface IGenericService<E extends BaseModel, R extends IGenericRepository<E>, V extends IGenericValidation<E, R>> {

	E findByIdActive(UUID id);
        
        // retorna lista simples sem paginação para o frontend
        List<E> findAllActiveList();

	E insert(E entity);
	E update(E entity);
	void delete(UUID id);

}
