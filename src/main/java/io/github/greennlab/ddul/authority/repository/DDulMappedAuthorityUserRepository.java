package io.github.greennlab.ddul.authority.repository;

import io.github.greennlab.ddul.authority.MappedAuthorityUser;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface DDulMappedAuthorityUserRepository extends Repository<MappedAuthorityUser, Long> {

  Set<MappedAuthorityUser> findAllByUserId(Long userId);

  Set<MappedAuthorityUser> saveAll(Iterable<MappedAuthorityUser> entities);

}
