package io.github.greennlab.ddul.code.service;

import static io.github.greennlab.ddul.DDulCacheConfiguration.CACHED_CODE;
import static io.github.greennlab.ddul.code.dto.CommonCodeDTO.mapped;

import io.github.greennlab.ddul.code.CommonCode;
import io.github.greennlab.ddul.code.dto.CommonCodeDTO;
import io.github.greennlab.ddul.code.repository.DDulCodeRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DDulCodeServiceImpl implements CodeService {

  private final DDulCodeRepository repository;


  @Override
  public List<CommonCodeDTO> searchBy(String group, String name) {
    final List<CommonCode> codes =
        repository.findAllByGroupAndNameContainsOrderByOrder(group, name);

    return codes.stream()
        .map(mapped::to)
        .collect(Collectors.toList());
  }

  @Override
  public CommonCodeDTO findBy(Long id) {
    return mapped.to(repository.findById(id).orElse(null));
  }

  @Cacheable(cacheNames = CACHED_CODE, key = "#group")
  @Override
  public List<CommonCodeDTO> findAllBy(String group) {
    final List<CommonCode> codes =
        repository.findAllByGroupOrderByOrder(group);

    return codes.stream()
        .map(mapped::to)
        .collect(Collectors.toList());
  }

  @Override
  public CommonCodeDTO findByGroupAndCode(String group, String code) {
    return mapped.to(repository.findByGroupAndCode(group, code));
  }

  @Transactional
  @CacheEvict(cacheNames = CACHED_CODE, key = "#dto.group")
  @Override
  public CommonCodeDTO save(CommonCodeDTO dto) {
    final Long id = dto.getId();

    if (null != id) {
      final CommonCode old = repository.findById(id).orElseThrow(NullPointerException::new);
      final String oldCode = old.getCode();
      final String newCode = dto.getCode();

      if (!Objects.equals(oldCode, newCode)) {
        repository.updateAllGroups(oldCode, newCode);
      }
    }

    final CommonCode saved = repository.save(mapped.by(dto));
    return mapped.to(saved);
  }

  @Transactional
  @Override
  public List<CommonCodeDTO> saveAll(List<CommonCodeDTO> dto) {
    return dto.stream().map(this::save).collect(Collectors.toList());
  }

}
