package com.github.greennlab.ddul.menu.web;

import com.github.greennlab.ddul.menu.Menu;
import com.github.greennlab.ddul.menu.service.DDulMenuService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/_menu")
@RequiredArgsConstructor
public class DDulMenuController {

  private final DDulMenuService service;


  @GetMapping
  public Menu.Dto getAllMenus(Long id) {
    return service.getAllMenus(id);
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
