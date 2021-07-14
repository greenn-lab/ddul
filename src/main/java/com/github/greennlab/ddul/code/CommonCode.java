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
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "CODE",
    uniqueConstraints = @UniqueConstraint(
        name = "CODE_UK1",
        columnNames = {"GRP", "COD"}
    )
)
@NoArgsConstructor
@Getter
@Setter
public class CommonCode extends Auditor {

  public static final CommonCodeOf mapped = Mappers.getMapper(CommonCodeOf.class);

  private static final long serialVersionUID = -8379883687091265041L;


  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "SYS")
  private String system;
  @Column(name = "GRP")
  private String group;
  @Column(name = "GRP_NM")
  private String groupName;
  @Column(name = "COD")
  private String code;
  @Column(name = "COD_NM")
  private String codeName;
  @Column(name = "ORD")
  private int order;


  public CommonCode(String group, String groupName) {
    this.group = group;
    this.groupName = groupName;
  }


  // -------------------------------------------------------
  // Underling
  // -------------------------------------------------------
  @Mapper
  public interface CommonCodeOf extends EntityDtoMapping<CommonCode, Dto> {

  }

  @Data
  public static class Dto {

    private Long id;
    private String system;
    private String group;
    private String groupName;
    private String code;
    private String codeName;
    private int order;
    private LocalDateTime deleted;

  }

}
