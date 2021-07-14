package com.github.greennlab.ddul.code.repository;

import com.github.greennlab.ddul.code.CommonCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DDulCodeRepository extends JpaRepository<CommonCode, Long> {

  List<CommonCode> findAllByGroupAndNameContainsOrderByOrder(String group, String codeName);

  List<CommonCode> findAllByGroupOrderByOrder(String group);

}
