package com.github.greennlab.ddul.file;

import com.github.greennlab.ddul.entity.Auditor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "FILE")
@Getter
@Setter
public class File extends Auditor {

  private static final long serialVersionUID = -7827691317250281752L;


  @Id
  private String id;

  private String name;
  private String path;
  private long size;
  private String mime;
  private String pack;
  private int access;

  private boolean removal;

}
