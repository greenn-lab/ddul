package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class BoardDTO {

  public static final BoardMapper mapped = Mappers.getMapper(BoardMapper.class);


  private Long id;
  private String group;
  private Long upperId;

  @NotEmpty
  private String title;

  private BoardAuthor author = new BoardAuthor();
  private boolean secret;
  private BoardContent content;
  private List<String> removeFileIds;


  @Mapper
  public interface BoardMapper extends EntityDtoMapping<Board, BoardDTO> {

  }

}
