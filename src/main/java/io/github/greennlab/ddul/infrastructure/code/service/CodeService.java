package io.github.greennlab.ddul.infrastructure.code.service;

import io.github.greennlab.ddul.infrastructure.code.dto.CommonCodeDTO;
import java.util.List;

public interface CodeService {

  List<CommonCodeDTO> searchBy(String group, String name);

  List<CommonCodeDTO> findAllBy(String group);

  CommonCodeDTO findBy(Long id);

  CommonCodeDTO findByGroupAndCode(String group, String code);

  CommonCodeDTO save(CommonCodeDTO dto);

  List<CommonCodeDTO> saveAll(List<CommonCodeDTO> dto);

  Long getNextId();

}
