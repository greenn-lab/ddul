package io.github.greennlab.ddul.infrastructure.article.dto;

import io.github.greennlab.ddul.infrastructure.article.Article;
import io.github.greennlab.ddul.infrastructure.file.dto.FileInputDTO;
import io.github.greennlab.ddul.infrastructure.user.User;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class ArticleInputDTO {

  public static final ArticleInputMapper mapped = Mappers.getMapper(ArticleInputMapper.class);

  @NotEmpty(groups = EDIT.class)
  private Long id;

  @NotEmpty
  private String category;
  private String depth;
  private User user;
  private String author;
  private String email;
  private String password;
  private String title;
  private String content;
  private List<FileInputDTO> attachFiles;
  private boolean secret;


  @Mapper
  public interface ArticleInputMapper extends EntityDtoMapping<Article, ArticleInputDTO> {

  }


  public interface EDIT {

  }

}
