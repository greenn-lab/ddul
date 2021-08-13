package com.github.greennlab.ddul.article;

import static com.github.greennlab.ddul.article.QArticle.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.github.greennlab.ddul.entity.BaseEntity;
import com.github.greennlab.ddul.file.File;
import com.github.greennlab.ddul.user.User;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ARTICLE")
@Getter
@Setter
@NoArgsConstructor
public class Article extends BaseEntity {

  private static final long serialVersionUID = 7808409788936959320L;

  public Article(
      User user,
      String author,
      String email,
      String password,
      String title,
      Integer read) {
    this.user = user;
    this.author = author;
    this.email = email;
    this.password = password;
    this.title = title;
    this.read = read;
  }

  @Transient
  @JsonProperty(access = Access.WRITE_ONLY)
  private Long pid;

  private Long bid;

  @Column(name = "ORD")
  private Integer order = 0;

  private Integer depth = 0;
  private String category;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User user;

  private String author;
  private String email;

  @JsonIgnore
  private String password;

  private boolean secret;
  private int read;

  private String title;
  private String content;

  @Setter(AccessLevel.NONE)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String fulltext;

  @OneToMany
  @JoinColumn(name = "PACK", referencedColumnName = "ID")
  private List<File> attachFiles;

  private String extra;

  @Transient
  @JsonProperty(access = Access.WRITE_ONLY)
  private String[] addFileIds;
  @Transient
  @JsonProperty(access = Access.WRITE_ONLY)
  private String[] removeFileIds;


  @PrePersist
  @PreUpdate
  public void removeHtmlTagInContent() {
    this.fulltext = content.replaceAll("<[^>]*>", "");
  }

}
