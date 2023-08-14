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
import server.stutino.domain.exam.dto.response.ExamDetailResponse;
import server.stutino.domain.exam.dto.response.ExamListResponse;
import server.stutino.domain.exam.dto.response.ExamQuestionOptionsResponse;
import server.stutino.domain.exam.dto.response.ExamQuestionResponse;
import server.stutino.domain.exam.entity.QuestionType;
import server.stutino.domain.exam.service.ExamService;
import server.stutino.support.docs.RestDocumentTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
                ),
                null
        );

        ExamQuestionRegisterRequest question2
                = new ExamQuestionRegisterRequest(
                        QuestionType.SUBJECT_QUESTION,
                "주관식 질문 1",
                20,
                null,
                "주관식 모범답안 1"
        );

        List<ExamQuestionRegisterRequest> questions
                = new ArrayList<>(List.of(question1, question2));

        ExamRegisterRequest request =
                new ExamRegisterRequest(
                        1L,
                        "시험1",
                        String.valueOf(LocalDate.now()),
                        String.valueOf(LocalDate.now()),
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

    @Test
    @DisplayName("시험 목록을 성공적으로 조회하는가?")
    void successFindExam() throws Exception {
        // give
        when(examService.findAllExam(any()))
                .thenReturn(List.of(
                        new ExamListResponse(
                                1L,
                                "시험 제목1",
                                LocalDateTime.now(),
                                LocalDateTime.now()
                        ),
                        new ExamListResponse(
                                1L,
                                "시험 제목1",
                                LocalDateTime.now(),
                                LocalDateTime.now()
                        ),
                        new ExamListResponse(
                                1L,
                                "시험 제목1",
                                LocalDateTime.now(),
                                LocalDateTime.now()
                        )
                ));

        // when
        ResultActions perform =
                mockMvc.perform(
                        get("/exam/program/{programId}", 1L));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("find all exam",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @WithMockUser(username = "testMentor", authorities = {"ROLE_MENTOR"})
    @DisplayName("시험 세부정보를 성공적으로 조회하는가?")
    void successFindExamById() throws Exception {
        // give
        List<ExamQuestionOptionsResponse> options1 = List.of(
                new ExamQuestionOptionsResponse(
                        "선택지 1",
                        true
                ),
                new ExamQuestionOptionsResponse(
                        "선택지 2",
                        true
                ),
                new ExamQuestionOptionsResponse(
                        "선택지 3",
                        false
                ),
                new ExamQuestionOptionsResponse(
                        "선택지 4",
                        true
                )
        );

        List<ExamQuestionOptionsResponse> options2 = List.of(
                new ExamQuestionOptionsResponse(
                        "선택지 1",
                        true
                ),
                new ExamQuestionOptionsResponse(
                        "선택지 2",
                        true
                ),
                new ExamQuestionOptionsResponse(
                        "선택지 3",
                        false
                ),
                new ExamQuestionOptionsResponse(
                        "선택지 4",
                        true
                )
        );

        List<ExamQuestionResponse> examQuestionRegisterResponse = List.of(
                new ExamQuestionResponse(
                        QuestionType.SUBJECT_QUESTION,
                        "주관식 시험 문제 1",
                        10,
                        "주관식 모범답안 1"
                ),
                new ExamQuestionResponse(
                        QuestionType.SUBJECT_QUESTION,
                        "주관식 시험 문제 2",
                        10,
                        "주관식 모범답안 2"
                ),
                new ExamQuestionResponse(
                        QuestionType.MULTIPLE_CHOICE_QUESTION,
                        "객관식 시험 문제 1",
                        10,
                        options1
                ),
                new ExamQuestionResponse(
                        QuestionType.MULTIPLE_CHOICE_QUESTION,
                        "객관식 시험 문제 2",
                        10,
                        options2
                )
        );

        when(examService.findExamById(any()))
                .thenReturn(new ExamDetailResponse(
                        1L,
                        "시험 제목1",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        examQuestionRegisterResponse
                ));

        // when
        ResultActions perform =
                mockMvc.perform(
                        get("/exam/{examId}", 1L));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("find exam by id",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
