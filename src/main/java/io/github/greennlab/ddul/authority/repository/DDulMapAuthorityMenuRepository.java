package io.github.greennlab.ddul.authority.repository;

import io.github.greennlab.ddul.authority.MapAuthorityMenu;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface DDulMapAuthorityMenuRepository extends Repository<MapAuthorityMenu, Long> {

  Set<MapAuthorityMenu> findAllByMenuId(Long menuId);

  Set<MapAuthorityMenu> saveAll(Iterable<MapAuthorityMenu> entities);

}
