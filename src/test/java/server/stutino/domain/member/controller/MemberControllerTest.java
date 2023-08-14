package server.stutino.domain.member.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.ResultActions;
import server.stutino.domain.member.dto.request.MenteeRegisterRequest;
import server.stutino.domain.member.dto.request.MentorRegisterRequest;
import server.stutino.domain.member.dto.response.MyPageResponse;
import server.stutino.domain.member.dto.response.MyProgramResponse;
import server.stutino.domain.member.entity.Lesson;
import server.stutino.domain.member.entity.Major;
import server.stutino.domain.member.service.MemberService;
import server.stutino.domain.program.entity.ProgramState;
import server.stutino.support.docs.RestDocumentTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentRequest;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentResponse;

@WebMvcTest(MemberController.class)
@DisplayName("AdminController 에서")
class MemberControllerTest extends RestDocumentTest {
    @MockBean private MemberService memberService;
    @Test
    @DisplayName("멘토를 성공적으로 등록하는가?")
    void successRegisterMentor() throws Exception {
        // given
        MentorRegisterRequest request
                = new MentorRegisterRequest(
                "test@naver.com",
                "홍길동",
                "0000",
                20,
                "한국공학대학교",
                Major.IT_MANAGEMENT,
                Lesson.OFFLINE,
                4,
                "잘부탁드립니다.");

        MockMultipartFile image = new MockMultipartFile("image", "image.txt",
                MediaType.MULTIPART_FORM_DATA_VALUE, "test image!".getBytes());

        MockMultipartFile certification = new MockMultipartFile("certification", "certificate.txt",
                MediaType.MULTIPART_FORM_DATA_VALUE, "mentor certificate certificate".getBytes());

        MockPart mockJsonPart = new MockPart("data", toRequestBody(request).getBytes());
        mockJsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform =
                mockMvc.perform(
                        multipart("/member/mentor")
                                .file(image)
                                .file(certification)
                                .part(mockJsonPart));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register mentor",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("멘티를 성공적으로 등록하는가?")
    void successRegisterMentee() throws Exception {
        // given
        MenteeRegisterRequest request
                = new MenteeRegisterRequest(
                "test@naver.com",
                "홍길동",
                "0000",
                15,
                "시흥중학교",
                2,
                "잘부탁드립니다.");

        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                MediaType.MULTIPART_FORM_DATA_VALUE, "Hello, World!".getBytes());

        MockPart mockJsonPart = new MockPart("data", toRequestBody(request).getBytes());
        mockJsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform =
                mockMvc.perform(
                        multipart("/member/mentee")
                                .file(file)
                                .part(mockJsonPart));
        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register mentee",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("마이페이지 정보를 성공적으로 조회하는가?")
    void successGetMyPageInfo() throws Exception {
        // given
        when(memberService.getMyPageInfo(any()))
                .thenReturn(
                        new MyPageResponse(
                                "정민창",
                                20,
                                "wjdalsckd777@naver.com",
                                "http://~",
                                List.of(
                                    new MyProgramResponse(
                                            1L,
                                            "정민창",
                                            "대학교",
                                            Major.COMPUTER,
                                            "subject",
                                            "programPlace",
                                            10,
                                            "시작 ~ 종료",
                                            "시작 ~ 종료",
                                            ProgramState.RECRUIT
                                    ),
                                    new MyProgramResponse(
                                            2L,
                                            "정민창",
                                            "대학교",
                                            Major.COMPUTER,
                                            "subject",
                                            "programPlace",
                                            10,
                                            "시작 ~ 종료",
                                            "시작 ~ 종료",
                                            ProgramState.OPEN
                                    ),
                                    new MyProgramResponse(
                                            3L,
                                            "정민창",
                                            "대학교",
                                            Major.COMPUTER,
                                            "subject",
                                            "programPlace",
                                            10,
                                            "시작 ~ 종료",
                                            "시작 ~ 종료",
                                            ProgramState.CLOSE
                                    ))
                        )
                );

        // when
        ResultActions perform =
                mockMvc.perform(
                        get("/member/my-page/{memberId}", 1L));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("find my page",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
