package io.github.greennlab.ddul.menu.service;

import io.github.greennlab.ddul.menu.Menu;
import io.github.greennlab.ddul.menu.Menu.Dto;
import io.github.greennlab.ddul.menu.repository.MenuRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("DDulMenuService")
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

  private final MenuRepository repository;


  @Override
  public Menu.Dto getAllMenus(Long id) {
    final Menu root = repository.findById(id).orElseThrow(NoSuchElementException::new);

    return Menu.mapped.to(root);
  }

  @Override
  public Dto save(Dto dto) {
    final Menu saved = repository.save(Menu.mapped.by(dto));
    return Menu.mapped.to(saved);
  }

  @Override
  public void saveAll(List<Dto> dtos) {
    final List<Menu> menus = dtos.stream()
        .map(Menu.mapped::by)
        .collect(Collectors.toList());

    repository.saveAll(menus);
  }

  @Override
  public Long getNextSequence() {
    return repository.getNextSequence();
  }

}
