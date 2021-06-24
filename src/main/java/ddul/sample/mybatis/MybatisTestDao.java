package ddul.sample.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

@Mapper
public interface MybatisTestDao {

  Object test(Object params, Pageable pageable);

}
