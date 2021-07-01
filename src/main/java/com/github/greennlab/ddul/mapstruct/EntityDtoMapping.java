package com.github.greennlab.ddul.mapstruct;

import org.mapstruct.factory.Mappers;

public interface EntityDtoMapping<ENTITY, DTO> {

  default <T extends EntityDtoMapping<ENTITY, DTO>> T mapping() {
    @SuppressWarnings("unchecked")
    final T mapper = (T) Mappers.getMapper(this.getClass());
    return mapper;
  }

  ENTITY by(DTO dto);

  DTO to(ENTITY user);

}
