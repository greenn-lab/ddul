package com.github.greennlab.ddul.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@SuppressWarnings("serial")
public class Auditor implements Serializable {

  public static final String ID_GENERATOR_NAME = "ID_GENERATOR_SQ";


  @CreatedBy
  @Column(updatable = false)
  @JsonIgnore
  private String creator;

  @LastModifiedBy
  @JsonIgnore
  private String updater;

  @CreatedDate
  @Column(updatable = false)
  @JsonIgnore
  private LocalDateTime created;

  @LastModifiedDate
  @JsonIgnore
  private LocalDateTime updated;

}
