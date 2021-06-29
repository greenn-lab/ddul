package com.github.greennlab.ddul.infrastructure.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Embeddable
@Getter
@Setter
public class Auditors implements Serializable {

  private static final long serialVersionUID = 3134283601115325198L;

  @CreatedBy
  private String createBy;

  @LastModifiedBy
  private String modifyBy;

  @CreatedDate
  private LocalDateTime createDt;

  @LastModifiedDate
  private LocalDateTime modifyDt;

  private LocalDateTime deleted;

}
