package com.github.greennlab.ddul.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.greennlab.ddul.user.User;
import java.io.Serializable;
import javax.persistence.Column;
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
  @JsonIgnoreProperties()
  private User user;

  @Column(name = "AU_NAME")
  private String name;

  @Column(name = "AU_EMAIL")
  private String email;

  @Column(name = "AU_PWD")
  @JsonIgnore
  private String password;

}
