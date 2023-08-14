package server.stutino.domain.admin.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import server.stutino.domain.admin.dto.request.PassedMentorRequest;
import server.stutino.domain.admin.dto.response.MemberListResponse;
import server.stutino.domain.admin.service.AdminService;
import server.stutino.domain.member.entity.Lesson;
import server.stutino.domain.member.entity.Major;
import server.stutino.support.docs.RestDocumentTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentRequest;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentResponse;

@WebMvcTest(AdminController.class)
@DisplayName("AdminController 에서")
class AdminControllerTest extends RestDocumentTest {
    @MockBean private AdminService adminService;

    @Test
    @DisplayName("관리자 페이지에서 멘토 목록을 성공저긍로 조회하는가?")
    void successFindAllMentor() throws Exception {
        // given
        when(adminService.findAllMember())
                .thenReturn(
                    List.of(
                        new MemberListResponse(
                                1L,
                                LocalDateTime.now(),
                                "wjdalsckd777@naver.com",
                                "정민창",
                                23,
                                "한국공학대학교",
                                Major.IT_MANAGEMENT,
                                Lesson.OFFLINE,
                                "certificated.pdf",
                                true
                        ),
                        new MemberListResponse(
                                1L,
                                LocalDateTime.now(),
                                "wjdalsckd777@naver.com",
                                "정민창",
                                23,
                                "한국공학대학교",
                                Major.IT_MANAGEMENT,
                                Lesson.OFFLINE,
                                "certificated.pdf",
                                true
                        )
                    )
                );

        // when
        ResultActions perform =
                mockMvc.perform(
                        get("/admin"));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("find all member for admin",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("멘토 등록 승인/거절을 성공적으로 등록하는가?")
    void successRegisterMentee() throws Exception {
        // given
        PassedMentorRequest request
                = new PassedMentorRequest(
                1L,
                true);

        // when
        ResultActions perform =
                mockMvc.perform(
                        post("/admin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toRequestBody(request)));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register isPassed mentor",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
