package io.github.greennlab.ddul.authority.repository;

import io.github.greennlab.ddul.authority.MapAuthorityMenu;
import java.util.Set;

public interface DDulMapAuthorityMenuQuerydslRepository {

  Set<MapAuthorityMenu> findAll();

}
