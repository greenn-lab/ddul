package ddul.sample.mybatis;

import java.time.LocalDateTime;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("MybatisTest")
public class MybatisTest {

  private static final long serialVersionUID = 2606142038336726913L;

  private String code;
  private String test;
  private String codeId;
  private String codeNm;
  private LocalDateTime createDt;

}
