package io.github.greennlab.ddul.code.controller;

import static io.github.greennlab.ddul.code.CommonCode.mapped;
import static java.util.stream.Collectors.toList;

import io.github.greennlab.ddul.code.CommonCode;
import io.github.greennlab.ddul.code.repository.DDulCodeRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
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
        .collect(toList());
  }

  @GetMapping("/{group}")
  public List<CommonCode.Dto> getCodes(@PathVariable String group) {
    return repository.findAllByGroupOrderByOrder(group).stream()
        .map(mapped::to)
        .collect(toList());
  }

  @PostMapping
  public CommonCode.Dto save(@Valid @RequestBody CommonCode.Dto dto) {
    final CommonCode commonCode = mapped.by(dto);
    return mapped.to(repository.save(commonCode));
  }

  @PostMapping("/codes")
  @Transactional
  public List<CommonCode.Dto> saveCodes(@Valid @RequestBody List<CommonCode.Dto> list) {
    return list.stream().map(this::save).collect(toList());
  }

  @PostMapping("/groups")
  @Transactional
  public List<CommonCode.Dto> saveGroups(@Valid @RequestBody List<CommonCode.Dto> list) {
    return list.stream().map(i -> {
      final CommonCode commonCode = mapped.by(i);
      final Optional<CommonCode> origin = repository.findById(commonCode.getId());

      origin.ifPresent(present -> {
        final String originCode = origin.get().getCode();
        final String inputCode = commonCode.getCode();

        if (!Objects.equals(originCode, inputCode)) {
          repository.saveByModifiedGroup(originCode, inputCode);
        }
      });

      return mapped.to(repository.save(commonCode));
    }).collect(toList());
  }

}
