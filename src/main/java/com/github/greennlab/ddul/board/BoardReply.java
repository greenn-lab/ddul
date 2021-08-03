package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.user.User;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BoardReply extends Auditor {

  @Id
  @GeneratedValue
  private Long id;

  private Long upperId;
  private Long boardId;
  private String content;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "name", column = @Column(name = "AU_NAME")),
      @AttributeOverride(name = "password", column = @Column(name = "AU_PWD")),
  })
  private BoardAuthor author;

  private boolean secret;

}
