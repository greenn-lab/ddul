package io.github.greennlab.ddul.infrastructure.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.github.greennlab.ddul.entity.JsonMap;
import io.github.greennlab.ddul.infrastructure.article.Article;
import io.github.greennlab.ddul.infrastructure.file.dto.FileInputDTO;
import io.github.greennlab.ddul.infrastructure.user.dto.UserDTO;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class ArticleDTO {

  public static final ArticleInputMapper mapped = Mappers.getMapper(ArticleInputMapper.class);


  @NotNull(groups = EDIT.class)
  private Long id;

  private Long pid;

  @NotEmpty
  private String category;
  private String depth;
  private UserDTO user;
  private String author;
  private String email;
  private String password;
  private boolean secret;

  @JsonProperty(access = Access.WRITE_ONLY)
  private int read;

  private String title;
  private String content;
  private JsonMap extra;
  private List<FileInputDTO> attachFiles;


  @Mapper
  public interface ArticleInputMapper extends EntityDtoMapping<Article, ArticleDTO> {

  }


  public interface EDIT {

  }

}
