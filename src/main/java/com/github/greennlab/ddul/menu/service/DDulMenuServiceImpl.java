package com.github.greennlab.ddul.menu.service;

import com.github.greennlab.ddul.menu.Menu;
import com.github.greennlab.ddul.menu.Menu.Dto;
import com.github.greennlab.ddul.menu.Menu.MenuOf;
import com.github.greennlab.ddul.menu.repository.DDulMenuRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DDulMenuServiceImpl implements DDulMenuService {

  private final DDulMenuRepository repository;


  @Override
  public Menu.Dto getAllMenus(Long id) {
    final Menu root = repository.findById(id).orElseThrow(NoSuchElementException::new);

    return MenuOf.map.to(root);
  }

  @Override
  public Dto save(Dto dto) {
    final Menu saved = repository.save(MenuOf.map.by(dto));
    return MenuOf.map.to(saved);
  }

  @Override
  public void saveAll(List<Dto> dtos) {
    final List<Menu> menus = dtos.stream()
        .map(MenuOf.map::by)
        .collect(Collectors.toList());

    repository.saveAll(menus);
  }

}
