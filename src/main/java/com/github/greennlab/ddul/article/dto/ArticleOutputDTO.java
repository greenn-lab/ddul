package com.github.greennlab.ddul.article.dto;

import com.github.greennlab.ddul.article.Article;
import com.github.greennlab.ddul.file.File;
import com.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import com.github.greennlab.ddul.user.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class ArticleOutputDTO {

  public static final ArticlePaginatedMapper mapped =
      Mappers.getMapper(ArticlePaginatedMapper.class);

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
  public interface ArticlePaginatedMapper extends EntityDtoMapping<Article, ArticleOutputDTO> {

  }

}
