package com.github.greennlab.ddul.board.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.greennlab.ddul.DDulMessageConfiguration.ExceptionMessageSource;
import com.github.greennlab.ddul.DDulSecurityConfiguration;
import com.github.greennlab.ddul.board.BoardDTO;
import com.github.greennlab.ddul.board.service.DDulBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = DDulBoardController.class,
    includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = DDulSecurityConfiguration.class
    )
)
class DDulBoardControllerTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  ExceptionMessageSource exceptionMessageSource;

  @MockBean
  DDulBoardService service;


  @Test
  void save() throws Exception {
    final BoardDTO dto = new BoardDTO();
    dto.setId(-1L);

    given(service.save(dto)).willReturn(dto);

    mvc.perform(
            post("/_board/notice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"title\":\"test\",\"content\":{\"body\":\"<h1>test<span>hello</span></h1>\"}}")
        )
        .andExpect(status().isCreated());
  }

}
