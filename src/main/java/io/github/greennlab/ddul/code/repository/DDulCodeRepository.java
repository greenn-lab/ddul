package io.github.greennlab.ddul.code.repository;

import io.github.greennlab.ddul.code.CommonCode;
import io.github.greennlab.ddul.entity.AFewRepository;
import java.util.List;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DDulCodeRepository extends AFewRepository<CommonCode> {

  List<CommonCode> findAllByGroupAndNameContainsOrderByOrder(String group, String name);

  List<CommonCode> findAllByGroupOrderByOrder(String group);

  @Where(clause = "REMOVAL != 'N'")
  CommonCode findByGroupAndCode(String group, String code);

  @Modifying
  @Query("UPDATE CommonCode c SET c.group = :after WHERE c.group = :before")
  int updateAllGroups(
      @Param("before") String before,
      @Param("after") String after);
}
