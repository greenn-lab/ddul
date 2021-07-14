package com.github.greennlab.ddul.menu.repository;

import com.github.greennlab.ddul.entity.AFewRepository;
import com.github.greennlab.ddul.menu.Menu;
import java.util.List;

public interface DDulMenuRepository extends AFewRepository<Menu, Long> {

  <S extends Menu> List<S> saveAll(Iterable<S> var1);

}
