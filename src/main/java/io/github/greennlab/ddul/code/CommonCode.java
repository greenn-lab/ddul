package io.github.greennlab.ddul.code;

import static io.github.greennlab.ddul.Application.DB_PREFIX;

import io.github.greennlab.ddul.entity.BaseEntity;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Entity
@Table(name = DB_PREFIX + "CODE")
@Getter
@Setter
public class CommonCode extends BaseEntity {

  public static final CommonCodeOf mapped = Mappers.getMapper(CommonCodeOf.class);

  private static final long serialVersionUID = -8379883687091265041L;


  @Column(name = "GRP_COD")
  private String group;

  @Column(name = "COD")
  private String code;

  @Column(name = "COD_NAME")
  private String name;

  @Column(name = "ORD")
  private int order;

  private boolean use;


  // -------------------------------------------------------
  // Underling
  // -------------------------------------------------------
  @Mapper
  public interface CommonCodeOf extends EntityDtoMapping<CommonCode, Dto> {

  }

  @Getter
  @Setter
  public static class Dto {

    private Long id;

    @NotEmpty
    private String group;

    @NotEmpty
    private String code;

    private String name;
    private int order;
    private boolean use;
    private boolean removal;

  }

}
