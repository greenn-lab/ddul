package io.github.greennlab.ddul.entity;

import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface AFewRepository<ENTITY> extends Repository<ENTITY, Long> {

  Optional<ENTITY> findById(Long id);

  <E extends ENTITY> E save(E entity);

}
