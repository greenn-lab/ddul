package io.github.greennlab.ddul.menu.controller;

import io.github.greennlab.ddul.menu.dto.MenuInputDTO;
import io.github.greennlab.ddul.menu.dto.MenuOutputDTO;
import io.github.greennlab.ddul.menu.service.MenuService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("DDulMenuController")
@RequestMapping("_menu")
@RequiredArgsConstructor
public class MenuController {

  private final MenuService service;


  @GetMapping
  public MenuOutputDTO getAllMenus(Long id) {
    return service.getAllMenus(id);
  }

  @GetMapping("id")
  public Long getNextSequence() {
    return service.getNextSequence();
  }

  @PostMapping("/add")
  public MenuOutputDTO save(@Valid @RequestBody MenuInputDTO menu) {
    return service.save(menu);
  }

  @PostMapping("/save")
  public void saveAll(@Valid @RequestBody List<MenuInputDTO> menus) {
    service.saveAll(menus);
  }
}
