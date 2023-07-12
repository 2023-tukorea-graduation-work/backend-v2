package server.stutino.domain.exam.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import server.stutino.domain.exam.dto.request.ExamQuestionOptionsRegisterRequest;
import server.stutino.domain.exam.dto.request.ExamQuestionRegisterRequest;
import server.stutino.domain.exam.dto.request.ExamRegisterRequest;
import server.stutino.domain.exam.entity.QuestionType;
import server.stutino.domain.exam.service.ExamService;
import server.stutino.support.docs.RestDocumentTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentRequest;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentResponse;

@WebMvcTest(ExamController.class)
@DisplayName("ExamController 에서")
class ExamControllerTest extends RestDocumentTest {
    @MockBean private ExamService examService;

    @Test
    @WithMockUser(username = "testMentor", authorities = {"ROLE_MENTOR"})
    @DisplayName("시험을 성공적으로 등록하는가?")
    void successRegisterExam() throws Exception {
        // give
        ExamQuestionRegisterRequest question1
                = new ExamQuestionRegisterRequest(
                        QuestionType.MULTIPLE_CHOICE_QUESTION,
                "객관식 질문 1",
                10,
                List.of(new ExamQuestionOptionsRegisterRequest(
                        "객관식 문항 1",
                        false
                        ),
                        new ExamQuestionOptionsRegisterRequest(
                                "객관식 문항 2",
                                true
                        ),new ExamQuestionOptionsRegisterRequest(
                                "객관식 문항 3",
                                false
                        )
                )
        );

        ExamQuestionRegisterRequest question2
                = new ExamQuestionRegisterRequest(
                        QuestionType.SUBJECT_QUESTION,
                "주관식 질문 1",
                20,
                "주관식 모범답안 1"
        );

        List<ExamQuestionRegisterRequest> questions
                = new ArrayList<>(List.of(question1, question2));

        ExamRegisterRequest request =
                new ExamRegisterRequest(
                        1L,
                        "시험1",
                        String.valueOf(LocalDateTime.now()),
                        String.valueOf(LocalDateTime.now()),
                        false,
                        questions
                );

        doNothing()
                .when(examService)
                .registerExam(any());

        // when
        ResultActions perform =
                mockMvc.perform(
                        post("/exam")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toRequestBody(request)));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register exam",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
