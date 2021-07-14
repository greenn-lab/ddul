package com.github.greennlab.ddul.code.repository;

import com.github.greennlab.ddul.code.CommonCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DDulCodeRepository extends JpaRepository<CommonCode, Long> {

  @Query(
      "select NEW CommonCode(c.group, c.groupName) from CommonCode c "
          + "where c.system = coalesce(:system, c.system) "
          + "and (c.group like concat('%', :keyword, '%') "
          + "or c.groupName like concat('%', :keyword, '%')) "
          + "group by c.group "
  )
  List<CommonCode> findAllByGroups(String system, String keyword);

  List<CommonCode> findAllByGroup(String group);

}
