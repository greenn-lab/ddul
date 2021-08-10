package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class BoardCommentDTO {

  public static final BoardReplyMapper mapped = Mappers.getMapper(BoardReplyMapper.class);


  private Long id;

  private Long upperId;

  @NotNull
  private Long boardId;

  @NotNull
  private String content;

  private BoardAuthor author;

  private boolean secret;


  // -------------------------------------------------------
  // Underling
  // -------------------------------------------------------
  @Mapper
  interface BoardReplyMapper extends EntityDtoMapping<BoardComment, BoardCommentDTO> {

  }
}
