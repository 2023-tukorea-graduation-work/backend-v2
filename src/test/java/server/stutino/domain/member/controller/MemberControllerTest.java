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
import server.stutino.domain.member.entity.Lesson;
import server.stutino.domain.member.entity.Major;
import server.stutino.domain.member.service.MemberService;
import server.stutino.support.docs.RestDocumentTest;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentRequest;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentResponse;

@WebMvcTest(MemberController.class)
@DisplayName("MemberController 에서")
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

        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                MediaType.MULTIPART_FORM_DATA_VALUE, "Hello, World!".getBytes());

        MockPart mockJsonPart = new MockPart("data", toRequestBody(request).getBytes());
        mockJsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform =
                mockMvc.perform(
                        multipart("/member/mentor")
                                .file(file)
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

}
