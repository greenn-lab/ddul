package com.github.greennlab.ddul.article;

import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.entity.JsonColumnToClobConverter;
import java.util.Properties;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ARTICLE_CATEGORY")
@Getter
@Setter
public class ArticleCategory extends Auditor {

  @Id
  private String category;

  private String type;

  @Column(name = "DSC")
  private String description;

  @Convert(converter = JsonColumnToClobConverter.class)
  private Properties props;

  private boolean removal;

}
