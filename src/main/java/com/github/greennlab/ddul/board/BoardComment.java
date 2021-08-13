package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.mybatis.MapperType;
import lombok.Getter;
import lombok.Setter;

@MapperType
@Getter
@Setter
public class BoardComment extends Auditor {

  private static final long serialVersionUID = 4913295068326207184L;


  private Long id;
  private Long bid;
  private Long boardId;
  private Integer depth;
  private Integer order;
  private String content;
  private BoardAuthor author;
  private boolean secret;

}
