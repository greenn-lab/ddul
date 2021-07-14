package com.github.greennlab.ddul.menu.service;

import com.github.greennlab.ddul.menu.Menu.Dto;
import java.util.List;

public interface DDulMenuService {

  Dto getAllMenus(Long menuId);

  Dto save(Dto dto);

  void saveAll(List<Dto> dtos);
}
