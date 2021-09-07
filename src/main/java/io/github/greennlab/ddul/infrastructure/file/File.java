package io.github.greennlab.ddul.infrastructure.file;

import io.github.greennlab.ddul.entity.Auditor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "_FILE")
@Getter
@Setter
public class File extends Auditor {

  private static final long serialVersionUID = -7827691317250281752L;


  @Id
  private String id;

  private String name;
  private String path;
  private long size;
  private String type;

  @Column(updatable = false)
  private int read;

  private boolean removal;

}
