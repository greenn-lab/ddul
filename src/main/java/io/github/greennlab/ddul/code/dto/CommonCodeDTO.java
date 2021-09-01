package io.github.greennlab.ddul.code.dto;

import io.github.greennlab.ddul.code.CommonCode;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class CommonCodeDTO {

  public static final CommonCodeMapper mapped = Mappers.getMapper(CommonCodeMapper.class);


  private Long id;

  @NotEmpty
  private String group;

  @NotEmpty
  private String code;

  private String name;
  private int order;
  private boolean use;
  private boolean removal;


  @Mapper
  public interface CommonCodeMapper extends EntityDtoMapping<CommonCode, CommonCodeDTO> {

  }

}
