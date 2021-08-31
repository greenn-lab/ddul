package io.github.greennlab.ddul.mapstruct;

public interface EntityDtoMapping<ENTITY, DTO> {

  ENTITY by(DTO dto);

  DTO to(ENTITY entity);

}
