package io.github.greennlab.ddul.infrastructure.menu.service;

import io.github.greennlab.ddul.infrastructure.menu.dto.MenuInputDTO;
import io.github.greennlab.ddul.infrastructure.menu.dto.MenuOutputDTO;
import java.util.List;

public interface MenuService {

  MenuOutputDTO getAllMenus(Long menuId);

  MenuOutputDTO save(MenuInputDTO menu);

  void saveAll(List<MenuInputDTO> menus);

}
