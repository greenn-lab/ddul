package io.github.greennlab.ddul.infrastructure.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.github.greennlab.ddul.entity.BaseEntity;
import io.github.greennlab.ddul.entity.JsonMap;
import io.github.greennlab.ddul.infrastructure.file.File;
import io.github.greennlab.ddul.infrastructure.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

@Entity
@Table(name = "_ARTICLE")
@Getter
@Setter
@NoArgsConstructor
public class Article extends BaseEntity {

  private static final long serialVersionUID = 7808409788936959320L;


  @Transient
  @JsonProperty(access = Access.WRITE_ONLY)
  private Long pid;

  @Column(updatable = false)
  private Long bunch;

  @Column(updatable = false)
  private Integer sequel = 0;

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

  @JsonIgnore
  @Setter(AccessLevel.NONE)
  private String fulltext;

  @Type(type = JsonMap.TYPE)
  private JsonMap extra;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "_ARTICLE_FILE",
      joinColumns = @JoinColumn(name = "ARTICLE_ID"),
      inverseJoinColumns = @JoinColumn(name = "FILE_ID")
  )
  private List<File> attachFiles = new ArrayList<>();


  @PrePersist
  @PreUpdate
  public void removeAllHtmlInContent() {
    if (null != content) {
      this.fulltext = content.replaceAll("<[^>]*>", "");
    }
  }

}
