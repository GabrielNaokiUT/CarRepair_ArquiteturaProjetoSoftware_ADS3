package br.edu.senai.fatesg.ads3.car_repair.core.repositories;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 * @param <E>
 */

@NoRepositoryBean
public interface IGenericRepository <E extends BaseModel>  extends JpaRepository<E, UUID> {
    Optional<E> findByIdAndAtivoTrue(UUID id);
    Page<E> findAllByAtivoTrue(Pageable pageable);
    List<E> findAllByAtivoTrue();
}