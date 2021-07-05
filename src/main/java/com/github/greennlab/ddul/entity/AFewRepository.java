package com.github.greennlab.ddul.entity;

import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface AFewRepository<ENTITY, ID> extends Repository<ENTITY, ID> {

  Optional<ENTITY> findById(ID id);

  <E extends ENTITY> E save(E user);

}
