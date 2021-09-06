package io.github.greennlab.ddul.menu.repository;

import static io.github.greennlab.ddul.entity.BaseEntity.ID_GENERATOR_NAME;

import io.github.greennlab.ddul.entity.AFewRepository;
import io.github.greennlab.ddul.menu.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface DDulMenuRepository extends AFewRepository<Menu> {

  List<Menu> saveAll(Iterable<Menu> menus);

  @Query(value = "SELECT " + ID_GENERATOR_NAME + ".nextval FROM dual", nativeQuery = true)
  Long getNextSequence();


}
