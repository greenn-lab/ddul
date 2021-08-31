package io.github.greennlab.ddul.article;

import static io.github.greennlab.ddul.Application.DB_PREFIX;

import io.github.greennlab.ddul.entity.Auditor;
import io.github.greennlab.ddul.entity.JsonMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = DB_PREFIX + "ARTICLE_CATEGORY")
@Getter
@Setter
public class ArticleCategory extends Auditor {

  private static final long serialVersionUID = 5389360734944260217L;


  @Id
  private String category;

  private String type;

  @Column(name = "DSC")
  private String description;

  @Type(type = "io.github.greennlab.ddul.entity.JsonMapType")
  private JsonMap props;

  private boolean removal;

}
