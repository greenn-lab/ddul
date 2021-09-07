package io.github.greennlab.ddul.infrastructure.authority.repository;

import io.github.greennlab.ddul.infrastructure.authority.MappedUserAuthority;
import java.util.Set;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface DDulMappedUserAuthorityRepository extends Repository<MappedUserAuthority, Long> {

  Set<MappedUserAuthority> findAllByUserId(Long userId);

  Set<MappedUserAuthority> saveAll(Iterable<MappedUserAuthority> entities);

  @Modifying
  @Query("UPDATE MappedUserAuthority t SET t.removal = true WHERE t.userId = :userId")
  void removeBeforeAll(Long userId);

}
