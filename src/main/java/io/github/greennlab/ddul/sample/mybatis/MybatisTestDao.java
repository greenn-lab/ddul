package io.github.greennlab.ddul.sample.mybatis;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MybatisTestDao {

  List<Map<String, Object>> test();

  Map<String, Object> test(Object params);

  Page<Map<String, Object>> test(Pageable pageable);

  Page<Map<String, Object>> test(Object params, Pageable pageable);

  void insert(Map<String, Object> params);
  void insert(MybatisTest params);
  void insert();

  void update(Map<String, Object> params);
  void update(MybatisTest params);
  void update();
}
