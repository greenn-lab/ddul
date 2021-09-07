package io.github.greennlab.ddul.authority.repository;

import io.github.greennlab.ddul.authority.MappedMenuAuthority;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface DDulMappedMenuRepositoryAuthority
    extends Repository<MappedMenuAuthority, Long>, DDulMappedMenuAuthorityQuerydslRepository {

  Set<MappedMenuAuthority> saveAll(Iterable<MappedMenuAuthority> entities);

}
