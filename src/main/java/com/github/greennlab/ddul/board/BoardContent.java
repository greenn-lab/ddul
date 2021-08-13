package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.mybatis.MapperType;
import lombok.Getter;
import lombok.Setter;

@MapperType
@Getter
@Setter
public class BoardContent extends Auditor {

  private static final long serialVersionUID = 954534381701799419L;

  private Long id;
  private String body;

  public String getText() {
    return body.replaceAll("<[^>]*>", "");
  }

}
