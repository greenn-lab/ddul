package io.github.greennlab.ddul.user;

import static io.github.greennlab.ddul.Application.DB_PREFIX;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.greennlab.ddul.entity.BaseEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = DB_PREFIX + "USER")
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {

  public static final String REGEXP_PASSWORD =
      "^(?=.*[~!@#$%^&*()_+`\\-=\\[\\]{};':\",./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\\S{6,}$";

  private static final long serialVersionUID = -7382145646927293876L;


  private String username;

  @JsonIgnore
  private String password;

  @Column(name = "PASSWORD_EXP")
  private LocalDate passwordExpired;

  private String email;
  private String name;
  private boolean lock;

}