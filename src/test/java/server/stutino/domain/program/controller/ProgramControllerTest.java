package server.stutino.domain.program.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import server.stutino.domain.member.entity.Lesson;
import server.stutino.domain.member.entity.Major;
import server.stutino.domain.program.dto.request.ProgramParticipateRequest;
import server.stutino.domain.program.dto.request.ProgramRegisterRequest;
import server.stutino.domain.program.dto.request.ProgramWeekRegisterRequest;
import server.stutino.domain.program.dto.response.ProgramDetailResponse;
import server.stutino.domain.program.dto.response.ProgramListResponse;
import server.stutino.domain.program.dto.response.ProgramWeekDetailResponse;
import server.stutino.domain.program.service.ProgramService;
import server.stutino.support.docs.RestDocumentTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentRequest;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentResponse;

@WebMvcTest(ProgramController.class)
@DisplayName("ProgramController 에서")
class ProgramControllerTest extends RestDocumentTest {
    @MockBean private ProgramService programService;
    @Test
    @WithMockUser(username = "testMentor", authorities = {"ROLE_MENTOR"})
    @DisplayName("프로그램을 성공적으로 등록하는가?")
    void successRegisterProgram() throws Exception {
        // give
        List<ProgramWeekRegisterRequest> programWeeks
                = new ArrayList<ProgramWeekRegisterRequest>();

        programWeeks.add(new ProgramWeekRegisterRequest(
                "프로그램 주차 정보1",
                "2023-07-01"
        ));
        programWeeks.add(new ProgramWeekRegisterRequest(
                "프로그램 주차 정보2",
                "2023-07-07"
        ));

        ProgramRegisterRequest request
                = new ProgramRegisterRequest(
                1L,
                "프로그램 테스트 제목1",
                "프로그램 테스트 상세내용1",
                "2023-06-30",
                "2023-97-12",
                "2023-06-15",
                "2023-06-21",
                10,
                "한국공학대학교",
                programWeeks
        );

        doNothing()
                .when(programService)
                    .registerProgram(any());


        // when
        ResultActions perform =
                mockMvc.perform(
                        post("/program")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toRequestBody(request))
                );

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register program",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("전체 프로그램을 성공적으로 조회하는가?")
    void successGetAll() throws Exception {
        // given
        when(programService.getProgramList(any()))
                .thenReturn(List.of(
                        new ProgramListResponse(
                                "정민창",
                                "한국공학대학교",
                                Major.IT_MANAGEMENT,
                                Lesson.OFFLINE,
                                1L,
                                "프로그램 테스트 제목1",
                                "프로그램 테스트 상세내용1",
                                LocalDate.now(),
                                LocalDate.now(),
                                10,
                                "서울특별시 강남구 ABC 빌딩",
                                0L
                        ),
                        new ProgramListResponse(
                                "유제빈",
                                "한국공학대학교",
                                Major.IT_MANAGEMENT,
                                Lesson.OFFLINE,
                                1L,
                                "프로그램 테스트 제목2",
                                "프로그램 테스트 상세내용2",
                                LocalDate.now(),
                                LocalDate.now(),
                                10,
                                "서울특별시 강남구 ABC 빌딩",
                                0L
                        )
                ));

        // when
        ResultActions perform =
                mockMvc.perform(
                        get("/program")
                            .param("keyword", ""));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("find all program",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("프로그램 상세 정보를 성공적으로 조회하는가?")
    void successGetProgramDetail() throws Exception {
        // given
        when(programService.getProgramDetail(any()))
                .thenReturn(
                        new ProgramDetailResponse(
                                1L,
                                "정민창",
                                "한국공학대학교",
                                4,
                                Major.IT_MANAGEMENT,
                                Lesson.OFFLINE,
                                "잘부탁드립니다.",
                                1L,
                                "프로그램 테스트 제목1",
                                "프로그램 테스트 상세내용1",
                                LocalDate.now(),
                                LocalDate.now(),
                                LocalDate.now(),
                                LocalDate.now(),
                                10,
                                "서울특별시 강남구 ABC 빌딩",
                                List.of(
                                        new ProgramWeekDetailResponse(
                                                1l,
                                                "프로그램 주차 내용1",
                                                LocalDate.now()
                                        ),
                                        new ProgramWeekDetailResponse(
                                                1l,
                                                "프로그램 주차 내용2",
                                                LocalDate.now()
                                        )
                                )
                        )
                );

        // when
        ResultActions perform =
                mockMvc.perform(
                        get("/program/{programId}", 1L));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("find program detail",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("프로그램 참여자 정보를 성공적으로 등록하는가?")
    void successRegisterParticipants() throws Exception {
        // given
        ProgramParticipateRequest request =
                new ProgramParticipateRequest(
                        1L,
                        1L
                );
        doNothing()
                .when(programService)
                    .registerParticipation(any());

        // when
        ResultActions perform =
                mockMvc.perform(
                        post("/program/participate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toRequestBody(request)));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register participants",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
