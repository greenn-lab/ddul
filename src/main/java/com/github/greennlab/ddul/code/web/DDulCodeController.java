package com.github.greennlab.ddul.code.web;

import com.github.greennlab.ddul.code.CommonCode;
import com.github.greennlab.ddul.code.repository.DDulCodeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/_code")
@RequiredArgsConstructor
public class DDulCodeController {

  private final DDulCodeRepository repository;


  @GetMapping
  public List<CommonCode.Dto> getGroupCodes(String system, String keyword) {
    if (!StringUtils.hasText(system)) {
      system = null;
    }

    return repository.findAllByGroups(system, keyword).stream()
        .map(CommonCode.mapped::to)
        .collect(Collectors.toList());
  }

  @GetMapping("/{group}")
  public List<CommonCode.Dto> getCodes(String group) {
    return repository.findAllByGroup(group).stream()
        .map(CommonCode.mapped::to)
        .collect(Collectors.toList());
  }

}
