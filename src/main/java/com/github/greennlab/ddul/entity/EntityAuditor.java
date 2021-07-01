package com.github.greennlab.ddul.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Getter
@Setter
@SuppressWarnings("serial")
public class EntityAuditor implements Serializable {

  private boolean deleted;

  @CreatedBy
  @Column(updatable = false)
  private String createBy;

  @LastModifiedBy
  private String modifyBy;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createDt;

  @LastModifiedDate
  private LocalDateTime modifyDt;

}
