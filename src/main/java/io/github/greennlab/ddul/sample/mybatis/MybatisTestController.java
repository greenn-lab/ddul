package io.github.greennlab.ddul.sample.mybatis;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

  @GetMapping("6")
  public Object test() {
    return dao.test();
  }

  @PostMapping("/insert/1")
  public void insert(@RequestParam Map<String, Object> params) {
    dao.insert(params);
  }

  @PostMapping("/insert/2")
  public void insert(MybatisTest mybatisTest) {
    dao.insert(mybatisTest);
  }

  @PostMapping("/insert/3")
  public void insert() {
    dao.insert();
  }


  @PostMapping("/update/1")
  public void update(@RequestParam Map<String, Object> params) {
    dao.update(params);
  }

  @PostMapping("/update/2")
  public void update(MybatisTest mybatisTest) {
    dao.update(mybatisTest);
  }

  @PostMapping("/update/3")
  public void update() {
    dao.update();
  }
}
