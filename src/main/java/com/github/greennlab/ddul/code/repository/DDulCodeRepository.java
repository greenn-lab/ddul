package com.github.greennlab.ddul.code.repository;

import com.github.greennlab.ddul.code.CommonCode;
import com.github.greennlab.ddul.entity.AFewRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DDulCodeRepository extends AFewRepository<CommonCode> {

  List<CommonCode> findAllByGroupAndNameContainsOrderByOrder(String group, String codeName);

  List<CommonCode> findAllByGroupOrderByOrder(String group);

  @Modifying
  @Query("UPDATE CommonCode c SET c.group = :after WHERE c.group = :before")
  int saveByModifiedGroup(
      @Param("before") String before,
      @Param("after") String after);
}
