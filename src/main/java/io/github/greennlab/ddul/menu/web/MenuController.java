package io.github.greennlab.ddul.menu.web;

import io.github.greennlab.ddul.menu.Menu;
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
  public Menu.Dto getAllMenus(Long id) {
    return service.getAllMenus(id);
  }

  @GetMapping("id")
  public Long getNextSequence() {
    return service.getNextSequence();
  }

  @PostMapping("/add")
  public Menu.Dto save(@Valid @RequestBody Menu.Dto dto) {
    return service.save(dto);
  }

  @PostMapping("/save")
  public void saveAll(@Valid @RequestBody List<Menu.Dto> dtos) {
    service.saveAll(dtos);
  }
}
