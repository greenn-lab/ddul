package io.github.greennlab.ddul.infrastructure.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.github.greennlab.ddul.infrastructure.article.ArticleComment;
import io.github.greennlab.ddul.infrastructure.user.dto.UserDTO;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class ArticleCommentDTO {

  public static final ArticleCommentMapper mapped = Mappers.getMapper(ArticleCommentMapper.class);


  @NotNull(groups = EDIT.class)
  private Long id;
  private Long bunch;
  private Integer depth = 0;
  private Integer sequel = 0;
  private Long articleId;
  private UserDTO user;
  private String author;
  private String email;

  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  private String comment;
  private boolean secret;


  @Mapper
  public interface ArticleCommentMapper extends
      EntityDtoMapping<ArticleComment, ArticleCommentDTO> {

  }

  // --------------------------------------------------
  // For JSR-303
  // --------------------------------------------------
  public interface EDIT {

  }

}
