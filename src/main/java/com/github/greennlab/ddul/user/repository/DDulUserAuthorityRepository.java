package com.github.greennlab.ddul.user.repository;

import com.github.greennlab.ddul.entity.AFewRepository;
import com.github.greennlab.ddul.user.UserAuthority;
import java.util.Set;

public interface DDulUserAuthorityRepository extends AFewRepository<UserAuthority> {

  Set<UserAuthority> findAllByUserId(Long userId);

}
