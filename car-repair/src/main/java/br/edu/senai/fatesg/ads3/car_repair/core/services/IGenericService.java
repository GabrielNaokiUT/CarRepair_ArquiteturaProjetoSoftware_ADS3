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
        
        // Método original com paginação — mantido para não quebrar o findAll existente
        Page<E> findAllActive(Pageable pageable);

        // Método novo sem paginação — necessário para o frontend receber um array simples
        List<E> findAllActiveList();

	E insert(E entity);
	E update(E entity);
	void delete(UUID id);
}
