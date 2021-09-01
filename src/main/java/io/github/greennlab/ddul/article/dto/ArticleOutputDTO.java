package io.github.greennlab.ddul.article.dto;

import io.github.greennlab.ddul.article.Article;
import io.github.greennlab.ddul.file.File;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import io.github.greennlab.ddul.user.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class ArticleOutputDTO {

  public static final ArticleOutputMapper mapped =
      Mappers.getMapper(ArticleOutputMapper.class);

  private Long id;
  private String category;
  private String depth;
  private User user;
  private String author;
  private String email;
  private String password;
  private String title;
  private String content;
  private boolean secret;
  private String read;
  private LocalDateTime created;
  private List<File> attachFiles;

  @Mapper
  public interface ArticleOutputMapper extends EntityDtoMapping<Article, ArticleOutputDTO> {

  }

}
