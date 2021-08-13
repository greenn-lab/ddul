package com.github.greennlab.ddul.board;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.file.File;
import com.github.greennlab.ddul.mybatis.MapperType;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@MapperType
@Getter
@Setter
public class Board extends Auditor {

  private static final long serialVersionUID = 5317379996515377215L;

  private Long id;
  private Long bid;
  private Integer depth = 0;
  private Integer order = 0;

  @NotEmpty
  private String group;

  private String title;
  private BoardAuthor author = new BoardAuthor();
  private boolean secret;
  private int accessCount;
  private BoardContent content;
  private boolean removal;

  private List<File> attachFiles;
  private List<BoardComment> comments;
  private List<BoardExtra> extras;

  @JsonProperty(access = Access.WRITE_ONLY)
  private Long pid;

  @JsonProperty(access = Access.WRITE_ONLY)
  private String[] addFileIds;

  @JsonProperty(access = Access.WRITE_ONLY)
  private String[] removeFileIds;

}
