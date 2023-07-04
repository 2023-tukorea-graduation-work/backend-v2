package server.stutino.domain.question.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import server.stutino.domain.question.dto.request.QuestionRegisterRequest;
import server.stutino.domain.question.dto.response.QuestionListResponse;
import server.stutino.domain.question.service.QuestionService;
import server.stutino.support.docs.RestDocumentTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentRequest;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentResponse;

@WebMvcTest(QuestionController.class)
@DisplayName("QuestionController 에서")
class QuestionControllerTest extends RestDocumentTest {
    @MockBean private QuestionService questionService;

    @Test
    @DisplayName("질문을 성공적으로 등록하는가?")
    void successRegisterQuestion() throws Exception {
        // give
        QuestionRegisterRequest request =
                new QuestionRegisterRequest(
                        1L,
                        1L,
                        "질문 내용1"
                );

        doNothing()
                .when(questionService)
                .registerQuestion(any());

        // when
        ResultActions perform =
                mockMvc.perform(
                        post("/question")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toRequestBody(request)));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register question",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("질문 목록을 성공적으로 조회하는가?")
    void successGetAllByProgram() throws Exception {
        // given
        when(questionService.getQuestionList(any()))
                .thenReturn(List.of(
                        new QuestionListResponse(
                                1L,
                                "질문 내용1",
                                "답변 내용1",
                                LocalDateTime.now(),
                                LocalDateTime.now()
                        ),
                        new QuestionListResponse(
                                1L,
                                "질문 내용2",
                                "답변 내용2",
                                LocalDateTime.now(),
                                LocalDateTime.now()
                        )
                ));

        // when
        ResultActions perform =
                mockMvc.perform(
                    get("/question/{programId}", 1L));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("find all question",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
