package io.github.greennlab.ddul.user.dto;

import static io.github.greennlab.ddul.user.User.REGEXP_PASSWORD;

import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import io.github.greennlab.ddul.user.User;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Getter
@Setter
public class UserDTO {

  public static final UserMapper mapped = Mappers.getMapper(UserMapper.class);


  private Long id;

  @NotEmpty
  private String username;

  @Pattern(regexp = REGEXP_PASSWORD)
  private String password;

  private LocalDate passwordExpired;

  @Email
  private String email;

  private String name;

  private boolean lock;


  // -------------------------------------------------------
  // Underling
  // -------------------------------------------------------
  @Mapper
  interface UserMapper extends EntityDtoMapping<User, UserDTO> {

  }

}
