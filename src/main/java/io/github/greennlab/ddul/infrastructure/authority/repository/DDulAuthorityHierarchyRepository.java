package io.github.greennlab.ddul.infrastructure.authority.repository;

import io.github.greennlab.ddul.infrastructure.authority.AuthorityHierarchy;
import org.springframework.data.repository.Repository;

public interface DDulAuthorityHierarchyRepository extends Repository<AuthorityHierarchy, Long> {

  AuthorityHierarchy findById(Long id);

}
