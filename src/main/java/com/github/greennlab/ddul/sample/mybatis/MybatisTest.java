package com.github.greennlab.ddul.sample.mybatis;

import com.github.greennlab.ddul.mybatis.MapperType;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@MapperType("mybatis-test")
public class MybatisTest {

  private static final long serialVersionUID = 2606142038336726913L;

  private String code;
  private String test;
  private String codeId;
  private String codeNm;
  private LocalDateTime createDt;

}
