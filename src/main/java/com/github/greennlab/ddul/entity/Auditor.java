package com.github.greennlab.ddul.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Auditor implements Serializable {

  public static final String ID_GENERATOR_NAME = "ID_GENERATOR_SQ";

  public static final String NOT_DELETED = "DELETED IS NULL";

  private static final long serialVersionUID = 1126743908870895361L;


  private LocalDateTime deleted;

  @CreatedBy
  @Column(updatable = false)
  private String creator;

  @LastModifiedBy
  private String updater;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime created;

  @LastModifiedDate
  private LocalDateTime updated;

}
