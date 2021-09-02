package io.github.greennlab.ddul;

import java.nio.file.Path;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ddul")
public class DDulProperties {

  private Boolean applySecurity = Boolean.FALSE;

  private Path fileStorage;


  public Boolean getApplySecurity() {
    return applySecurity;
  }

  public void setApplySecurity(Boolean applySecurity) {
    this.applySecurity = applySecurity;
  }

  public Path getFileStorage() {
    return fileStorage;
  }

  public void setFileStorage(Path fileStorage) {
    this.fileStorage = fileStorage;
  }

}
