package ddul.sample.mybatis;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper
public interface MybatisTestDao {

  Page<Map<String, Object>> test(Object params);

  Page<Map<String, Object>> test(Pageable pageable);

  Page<Map<String, Object>> test(Object params, Pageable pageable);

}
