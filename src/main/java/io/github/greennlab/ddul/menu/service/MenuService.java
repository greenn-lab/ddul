package io.github.greennlab.ddul.menu.service;

import io.github.greennlab.ddul.menu.dto.MenuInputDTO;
import io.github.greennlab.ddul.menu.dto.MenuOutputDTO;
import java.util.List;

public interface MenuService {

  MenuOutputDTO getAllMenus(Long menuId);

  MenuOutputDTO save(MenuInputDTO dto);

  void saveAll(List<MenuInputDTO> dtos);

  Long getNextSequence();
}
