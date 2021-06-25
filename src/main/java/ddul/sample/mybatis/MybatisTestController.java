package ddul.sample.mybatis;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample/mybatis")
@RequiredArgsConstructor
public class MybatisTestController {

  private final MybatisTestDao dao;

  @GetMapping
  public Object test(@RequestParam Map<String, String> params, Pageable pageable) {
    return dao.test(params, pageable);
  }

  @GetMapping("2")
  public Object test(@RequestParam Map<String, String> params) {
    return dao.test(params);
  }

  @GetMapping("3")
  public Object test(MybatisTest testParam, Pageable pageable) {
    return dao.test(testParam, pageable);
  }

  @GetMapping("4")
  public Object test(MybatisTest testParam) {
    return dao.test(testParam);
  }

  @GetMapping("5")
  public Object test(Pageable pageable) {
    return dao.test(pageable);
  }

}
