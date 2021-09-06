package io.github.greennlab.ddul.menu.service;

import io.github.greennlab.ddul.menu.Menu;
import io.github.greennlab.ddul.menu.Menu.Dto;
import io.github.greennlab.ddul.menu.repository.DDulMenuRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DDulMenuServiceImpl implements MenuService {

  private final DDulMenuRepository repository;


  @Override
  public Menu.Dto getAllMenus(Long id) {
    final Optional<Menu> root = repository.findById(id);
    return Menu.mapped.to(root.orElse(null));
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
