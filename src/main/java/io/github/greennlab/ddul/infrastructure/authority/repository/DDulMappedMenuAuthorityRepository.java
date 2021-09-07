package io.github.greennlab.ddul.infrastructure.authority.repository;

import io.github.greennlab.ddul.infrastructure.authority.MappedMenuAuthority;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface DDulMappedMenuAuthorityRepository
    extends Repository<MappedMenuAuthority, Long>, DDulMappedMenuAuthorityQuerydslRepository {

  Set<MappedMenuAuthority> findAllByMenuId(Long menuId);

  Set<MappedMenuAuthority> saveAll(Iterable<MappedMenuAuthority> entities);

}
