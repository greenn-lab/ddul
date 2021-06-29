package com.github.greennlab.ddul.sample.mybatis;

import com.github.greennlab.ddul.infrastructure.entity.Mappable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MybatisTest implements Mappable {

  private static final long serialVersionUID = 2606142038336726913L;

  private String code;
  private String test;
  private String codeId;
  private String codeNm;
  private LocalDateTime createDt;

}
