package io.github.greennlab.ddul.infrastructure.code.controller;

import static java.util.stream.Collectors.toList;

import io.github.greennlab.ddul.infrastructure.code.dto.CommonCodeDTO;
import io.github.greennlab.ddul.infrastructure.code.service.CodeService;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("DDulCodeController")
@RequestMapping("${ddul.api.code:_code}")
@RequiredArgsConstructor
@Slf4j
public class CodeController {

  private final CodeService service;


  @GetMapping
  public List<CommonCodeDTO> getGroupCodes(
      @RequestParam(required = false) String group,
      @RequestParam(required = false) String keyword
  ) {
    return service.searchBy(group, keyword);
  }

  @GetMapping("{group}")
  public List<CommonCodeDTO> getCodes(@PathVariable String group) {
    return service.findAllBy(group);
  }

  @PostMapping
  public CommonCodeDTO save(@Valid @RequestBody CommonCodeDTO dto) {
    return service.save(dto);
  }

  @PostMapping("all")
  @Transactional
  public List<CommonCodeDTO> saveAll(@Valid @RequestBody List<CommonCodeDTO> list) {
    return list.stream().map(this::save).collect(toList());
  }

  @GetMapping("id")
  public Long getNextId() {
    return service.getNextId();
  }

}
