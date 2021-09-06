package io.github.greennlab.ddul.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@SuppressWarnings("serial")
public class BaseEntity extends Auditor {

  public static final String ID_GENERATOR_NAME = "ID_GENERATOR_SQ";

  public static final String NOT_REMOVAL = "REMOVAL = 'N'";


  @Id
  @SequenceGenerator(name = ID_GENERATOR_NAME, sequenceName = ID_GENERATOR_NAME, allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = ID_GENERATOR_NAME)
  private Long id;

  private boolean removal;

}
