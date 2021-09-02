package io.github.greennlab.ddul.authority.repository;

import io.github.greennlab.ddul.authority.MapAuthorityUser;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface DDulMapAuthorityUserRepository extends Repository<MapAuthorityUser, Long> {

  Set<MapAuthorityUser> findAllByUserId(Long userId);

  Set<MapAuthorityUser> saveAll(Iterable<MapAuthorityUser> entities);

}
