package com.github.greennlab.ddul.sample.validation;

import com.github.greennlab.ddul.mybatis.MapperType;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@MapperType
public class ValidationTest {

  @NotEmpty
  private String name;

  @Range(min = 1, max = 200)
  private int age;

  @PastOrPresent
  private LocalDateTime birthday;

}
