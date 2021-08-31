package io.github.greennlab.ddul.sample.validation;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ValidationTestDao {

  Page<Map<String, Object>> test(Object params);

  Page<Map<String, Object>> test(Pageable pageable);

  Page<Map<String, Object>> test(Object params, Pageable pageable);

}
