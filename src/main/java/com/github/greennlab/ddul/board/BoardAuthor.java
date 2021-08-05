package com.github.greennlab.ddul.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.greennlab.ddul.user.User;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class BoardAuthor implements Serializable {

  private static final long serialVersionUID = -1677984264559532616L;


  @OneToOne
  @JoinColumn(name = "USER_ID")
  private User user;

  private String name;
  private String email;

  @JsonIgnore
  private String password;

}
