package com.github.greennlab.ddul.user.repository;

import com.github.greennlab.ddul.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Page<User> findAll(Pageable pageable);

  <E extends User> E saveAndFlush(E entity);

}
