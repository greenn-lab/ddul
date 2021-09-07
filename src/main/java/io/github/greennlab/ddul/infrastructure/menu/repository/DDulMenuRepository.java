package io.github.greennlab.ddul.infrastructure.menu.repository;

import io.github.greennlab.ddul.entity.AFewRepository;
import io.github.greennlab.ddul.infrastructure.menu.Menu;
import java.util.List;

public interface DDulMenuRepository extends AFewRepository<Menu> {

  List<Menu> saveAll(Iterable<Menu> menus);

}
