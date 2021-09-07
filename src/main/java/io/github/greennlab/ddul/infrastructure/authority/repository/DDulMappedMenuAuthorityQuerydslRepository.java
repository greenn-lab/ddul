package io.github.greennlab.ddul.infrastructure.authority.repository;

import io.github.greennlab.ddul.infrastructure.authority.MappedMenuAuthority;
import java.util.Set;

public interface DDulMappedMenuAuthorityQuerydslRepository {

  Set<MappedMenuAuthority> findAll();

}
