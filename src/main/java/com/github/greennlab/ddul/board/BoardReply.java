package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BOARD_REPLY")
@Getter
@Setter
public class BoardReply extends BaseEntity {

  private static final long serialVersionUID = 4913295068326207184L;


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
