package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.entity.Auditor;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Board extends Auditor {

  private static final long serialVersionUID = 5317379996515377215L;


  @Id
  @GeneratedValue
  private Long id;

  private Long upperId;
  private String title;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "name", column = @Column(name = "AU_NAME")),
      @AttributeOverride(name = "email", column = @Column(name = "AU_EMAIL")),
      @AttributeOverride(name = "password", column = @Column(name = "AU_PWD")),
  })
  private BoardAuthor author = new BoardAuthor();

  private boolean secret;

  @Column(name = "ACS_COUNT")
  private int accessCount;
}
