package com.github.greennlab.ddul.code.web;

import static com.github.greennlab.ddul.code.CommonCode.mapped;

import com.github.greennlab.ddul.code.CommonCode;
import com.github.greennlab.ddul.code.repository.DDulCodeRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/_code")
@RequiredArgsConstructor
public class DDulCodeController {

  private final DDulCodeRepository repository;


  @GetMapping
  public List<CommonCode.Dto> getGroupCodes(String group, String keyword) {
    return repository.findAllByGroupAndNameContainsOrderByOrder(group, keyword).stream()
        .map(mapped::to)
        .collect(Collectors.toList());
  }

  @GetMapping("/{group}")
  public List<CommonCode.Dto> getCodes(@PathVariable String group) {
    return repository.findAllByGroupOrderByOrder(group).stream()
        .map(mapped::to)
        .collect(Collectors.toList());
  }

  @PostMapping
  public CommonCode.Dto save(@Valid @RequestBody CommonCode.Dto dto) {
    final CommonCode code = repository.save(mapped.by(dto));
    return mapped.to(code);
  }

}
