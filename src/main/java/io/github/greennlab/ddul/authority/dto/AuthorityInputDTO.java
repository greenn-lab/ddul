package io.github.greennlab.ddul.authority.dto;

import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class AuthorityInputDTO {

  @Mapper
  public interface AuthorityInputMapper extends EntityDtoMapping<Authority, AuthorityInputDTO> {

  }

  public static final AuthorityInputMapper mapped = Mappers.getMapper(AuthorityInputMapper.class);


  private Long id;
  private Long pid;
  private String role;
  private String description;

}
