package io.github.greennlab.ddul;

import io.github.greennlab.ddul.entity.DDulPhysicalNamingStrategy;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ObjectUtils;

@ConfigurationProperties("ddul")
@Getter
@Setter
public class DDulProperties {

  private final HibernateProperties hibernateProperties;

  public DDulProperties(HibernateProperties hibernateProperties) {
    if (ObjectUtils.isEmpty(hibernateProperties.getNaming().getPhysicalStrategy())) {
      hibernateProperties.getNaming()
          .setPhysicalStrategy(DDulPhysicalNamingStrategy.class.getName());
    }

    this.hibernateProperties = hibernateProperties;
  }

  private Boolean applySecurity = Boolean.FALSE;

  private Path fileStorage = Paths.get(System.getProperty("java.io.tmpdir"));

  private String tablePrefix = "";


}
