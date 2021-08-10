package com.github.greennlab.ddul.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SuppressWarnings("serial")
public class BaseEntity extends Auditor implements Serializable {

  public static final String ID_GENERATOR_NAME = "ID_GENERATOR_SQ";

  @Id
  @SequenceGenerator(name = ID_GENERATOR_NAME, sequenceName = ID_GENERATOR_NAME)
  @GeneratedValue(strategy = SEQUENCE, generator = ID_GENERATOR_NAME)
  private Long id;

}
