package io.github.greennlab.ddul.infrastructure.menu.service;

import io.github.greennlab.ddul.infrastructure.menu.Menu;
import io.github.greennlab.ddul.infrastructure.menu.dto.MenuInputDTO;
import io.github.greennlab.ddul.infrastructure.menu.dto.MenuOutputDTO;
import io.github.greennlab.ddul.infrastructure.menu.repository.DDulMenuRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DDulMenuService")
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

  private final DDulMenuRepository repository;


  @Override
  public MenuOutputDTO getAllMenus(Long id) {
    final Optional<Menu> root = repository.findById(id);
    return MenuOutputDTO.mapped.to(root.orElse(null));
  }

  @Override
  public MenuOutputDTO save(MenuInputDTO menu) {
    final Menu saved = repository.save(MenuInputDTO.mapped.by(menu));
    return MenuOutputDTO.mapped.to(saved);
  }

  @Override
  public void saveAll(List<MenuInputDTO> menus) {
    final List<Menu> items = menus.stream()
        .map(MenuInputDTO.mapped::by)
        .collect(Collectors.toList());

    repository.saveAll(items);
  }

}
