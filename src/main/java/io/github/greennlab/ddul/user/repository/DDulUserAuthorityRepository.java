package io.github.greennlab.ddul.user.repository;

import io.github.greennlab.ddul.entity.AFewRepository;
import io.github.greennlab.ddul.user.UserAuthority;
import java.util.Set;

public interface DDulUserAuthorityRepository extends AFewRepository<UserAuthority> {

  Set<UserAuthority> findAllByUserId(Long userId);

}
