package io.github.greennlab.ddul.authority.repository;

import io.github.greennlab.ddul.authority.MappedAuthorityMenu;
import java.util.Set;

public interface DDulMappedAuthorityMenuQuerydslRepository {

  Set<MappedAuthorityMenu> findAll();

}
