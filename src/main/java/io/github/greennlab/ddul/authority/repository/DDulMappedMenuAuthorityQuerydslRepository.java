package io.github.greennlab.ddul.authority.repository;

import io.github.greennlab.ddul.authority.MappedMenuAuthority;
import java.util.Set;

public interface DDulMappedMenuAuthorityQuerydslRepository {

  Set<MappedMenuAuthority> findAll();

}
