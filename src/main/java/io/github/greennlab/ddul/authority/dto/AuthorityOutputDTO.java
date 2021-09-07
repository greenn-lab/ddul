package io.github.greennlab.ddul.authority.dto;

import io.github.greennlab.ddul.authority.AuthorityHierarchy;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import java.util.ArrayList;
import java.util.List;
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
  private boolean removal;
  private List<AuthorityOutputDTO> children = new ArrayList<>();


  @Mapper
  public interface AuthorityOutputMapper extends
      EntityDtoMapping<AuthorityHierarchy, AuthorityOutputDTO> {

  }

}
