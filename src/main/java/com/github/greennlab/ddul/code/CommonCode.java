package com.github.greennlab.ddul.code;

import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "CODE")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
public class CommonCode extends Auditor {

  public static final CommonCodeOf mapped = Mappers.getMapper(CommonCodeOf.class);

  private static final long serialVersionUID = -8379883687091265041L;


  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "GRP_COD")
  private String group;

  @Column(name = "COD")
  private String code;

  @Column(name = "COD_NM")
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
    private LocalDateTime deleted;

  }

}
