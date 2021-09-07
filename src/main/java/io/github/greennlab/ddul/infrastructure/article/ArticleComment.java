package io.github.greennlab.ddul.infrastructure.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.greennlab.ddul.entity.BaseEntity;
import io.github.greennlab.ddul.infrastructure.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "_ARTICLE_COMMENT")
@Getter
@Setter
public class ArticleComment extends BaseEntity {

  private Long bunch;

  @Column(updatable = false)
  private Integer sequel = 0;

  @Column(updatable = false)
  private Integer depth = 0;

  @ManyToOne
  @JoinColumn(name = "USER_ID", updatable = false)
  private User user;

  private String author;
  private String email;

  @JsonIgnore
  private String password;

  private String comment;
  private boolean secret;

}
