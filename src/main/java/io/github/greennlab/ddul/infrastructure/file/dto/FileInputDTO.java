package io.github.greennlab.ddul.infrastructure.file.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInputDTO {

  @NotEmpty(groups = EDIT.class)
  private String id;

  @NotEmpty
  private String name;

  @NotEmpty
  private String path;

  private long size;
  private String type;
  private boolean removal;


  // -------------------------------------------------------
  // For JSR-303
  // -------------------------------------------------------
  public interface EDIT {

  }
}
