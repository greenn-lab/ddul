package io.github.greennlab.ddul.mybatis;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudDao<ENTITY> {

  Page<ENTITY> getPage(Map<String, Object> params, Pageable pageable);

  ENTITY findById(Object entity);

  void insert(ENTITY entity);

  void update(ENTITY entity);

  void delete(ENTITY entity);

}
