package io.github.greennlab.ddul.authority.dto;

import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class AuthorityOutputDTO {

  public static final AuthorityOutputMapper mapped = Mappers.getMapper(AuthorityOutputMapper.class);

  private Long id;
  private Long pid;
  private String role;
  private String description;
  private Set<AuthorityOutputDTO> children = new HashSet<>();


  @Mapper
  public interface AuthorityOutputMapper extends EntityDtoMapping<Authority, AuthorityOutputDTO> {

  }

}
