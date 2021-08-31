package io.github.greennlab.ddul.menu.service;

import io.github.greennlab.ddul.menu.Menu.Dto;
import java.util.List;

public interface MenuService {

  Dto getAllMenus(Long menuId);

  Dto save(Dto dto);

  void saveAll(List<Dto> dtos);

  Long getNextSequence();
}
