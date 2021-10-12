package io.github.greennlab.ddul.infrastructure.article.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.infrastructure.article.Article;
import io.github.greennlab.ddul.infrastructure.article.dto.ArticleInputDTO.ArticleInputMapper;
import io.github.greennlab.ddul.infrastructure.file.dto.FileInputDTO;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class ArticleInputDTOMapperTest {

  @Test
  void shouldMapping() {
    final ArticleInputDTO articleInputDTO = new ArticleInputDTO();

    final FileInputDTO fileInputDTO = new FileInputDTO();
    fileInputDTO.setId("test1");

    articleInputDTO.setAttachFiles(Collections.singletonList(fileInputDTO));

    final Article by = ArticleInputDTO.mapped.by(articleInputDTO);

    assertThat(by.getAttachFiles()).isNotNull();
    assertThat(by.getAttachFiles().size()).isSameAs(1);
  }

}
