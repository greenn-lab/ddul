package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.mybatis.MapperType;
import lombok.Getter;
import lombok.Setter;

@MapperType
@Getter
@Setter
public class BoardExtra extends Auditor {

  private static final long serialVersionUID = -7114730479328650636L;

  private Long boardId;
  private String name;
  private String value;
  private String value2;

}
