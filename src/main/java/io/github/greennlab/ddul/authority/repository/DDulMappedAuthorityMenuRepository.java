package io.github.greennlab.ddul.authority.repository;

import io.github.greennlab.ddul.authority.MappedAuthorityMenu;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface DDulMappedAuthorityMenuRepository
    extends Repository<MappedAuthorityMenu, Long>, DDulMappedAuthorityMenuQuerydslRepository {

  Set<MappedAuthorityMenu> saveAll(Iterable<MappedAuthorityMenu> entities);

}
