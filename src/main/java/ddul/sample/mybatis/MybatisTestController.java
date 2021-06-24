package ddul.sample.mybatis;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample/mybatis")
@RequiredArgsConstructor
public class MybatisTestController {

  private final MybatisTestDao dao;

  @GetMapping
  public Object test(HttpServletRequest request, Pageable pageable) {
    return dao.test(request.getParameterMap(), pageable);
  }

}
