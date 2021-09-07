package io.github.greennlab.ddul.infrastructure.user.repository;

import io.github.greennlab.ddul.entity.AFewRepository;
import io.github.greennlab.ddul.infrastructure.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DDulUserRepository extends AFewRepository<User> {

  User findByUsername(String username);

  Page<User> findAll(Pageable pageable);

  <E extends User> E saveAndFlush(E entity);

}
