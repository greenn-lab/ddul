package io.github.greennlab.ddul.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.github.greennlab.ddul.entity.BaseEntity;
import io.github.greennlab.ddul.entity.JsonMap;
import io.github.greennlab.ddul.file.File;
import io.github.greennlab.ddul.user.User;
import java.util.List;
import javax.persistence.CascadeType;
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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "ARTICLE")
@Getter
@Setter
@NoArgsConstructor
public class Article extends BaseEntity {

  private static final long serialVersionUID = 7808409788936959320L;


  @Transient
  @JsonProperty(access = Access.WRITE_ONLY)
  private Long pid;

  @Column(updatable = false)
  private Long bid;

  @Column(name = "ORD", updatable = false)
  private Integer order = 0;

  @Column(updatable = false)
  private Integer depth = 0;

  private String category;

  @ManyToOne
  @JoinColumn(name = "USER_ID", updatable = false)
  private User user;

  private String author;
  private String email;

  @JsonIgnore
  private String password;

  private boolean secret;

  @Column(updatable = false)
  private int read;

  private String title;
  private String content;

  @JsonProperty(access = Access.WRITE_ONLY)
  @Setter(AccessLevel.NONE)
  private String fulltext;

  @OneToMany(cascade = {CascadeType.MERGE})
  @JoinColumn(name = "PACK", referencedColumnName = "ID", insertable = false)
  @Where(clause = NOT_REMOVAL)
  private List<File> attachFiles;

  @Type(type = JsonMap.TYPE)
  private JsonMap extra;


  @PrePersist
  @PreUpdate
  public void removeHtmlTagInContent() {
    if (null != content) {
      this.fulltext = content.replaceAll("<[^>]*>", "");
    }
  }

}
