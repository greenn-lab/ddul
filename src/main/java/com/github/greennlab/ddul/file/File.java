package com.github.greennlab.ddul.file;

import com.github.greennlab.ddul.entity.Auditor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class File extends Auditor {

  @Id
  private String id;

  private String name;
  private String path;
  private long size;

  @Column(name = "CONT_TYPE")
  private String contentType;

  @Column(name = "GRP")
  private String group;

  @Column(name = "ACS_COUNT")
  private int accessCount;

}
