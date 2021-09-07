package io.github.greennlab.ddul.authority.repository;

import io.github.greennlab.ddul.authority.MappedAuthorityMenu;
import io.github.greennlab.ddul.authority.MappedTeamAuthority;
import org.springframework.data.repository.Repository;

public interface DDulMappedAuthorityMenuRepository
    extends Repository<MappedAuthorityMenu, Long>, DDulMappedAuthorityMenuQuerydslRepository {

  void saveAll(Iterable<MappedTeamAuthority> entities);

}
