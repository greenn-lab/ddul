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
public class BoardComment extends BaseEntity {

  private static final long serialVersionUID = 4913295068326207184L;


  private Long bid;
  private Integer depth;

  @Column(name = "ORD")
  private Integer order;

  private Long boardId;
  private String content;

  @Embedded
  private BoardAuthor author;

  private boolean secret;

}
