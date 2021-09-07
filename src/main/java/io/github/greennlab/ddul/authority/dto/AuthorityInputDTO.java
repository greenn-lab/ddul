package io.github.greennlab.ddul.authority.dto;

import io.github.greennlab.ddul.authority.AuthorityHierarchy;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class AuthorityInputDTO {

  public static final AuthorityInputMapper mapped = Mappers.getMapper(AuthorityInputMapper.class);


  private Long id;
  private Long pid;
  private String role;
  private String description;
  private boolean removal;


  @Mapper
  public interface AuthorityInputMapper extends
      EntityDtoMapping<AuthorityHierarchy, AuthorityInputDTO> {

  }

}
