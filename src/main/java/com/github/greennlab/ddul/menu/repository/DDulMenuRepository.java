package com.github.greennlab.ddul.menu.repository;

import com.github.greennlab.ddul.entity.AFewRepository;
import com.github.greennlab.ddul.menu.Menu;
import java.util.List;
import org.hibernate.annotations.Where;

public interface DDulMenuRepository extends AFewRepository<Menu, Long> {

  @Where(clause = "REMOVAL = 'N'")
  <S extends Menu> S findById(long id);

  <S extends Menu> List<S> saveAll(Iterable<S> var1);

}
