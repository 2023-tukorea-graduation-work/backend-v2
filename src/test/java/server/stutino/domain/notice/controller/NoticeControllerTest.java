package server.stutino.domain.notice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import server.stutino.domain.notice.dto.request.NoticeRegisterRequest;
import server.stutino.domain.notice.dto.response.NoticeListResponse;
import server.stutino.domain.notice.service.NoticeService;
import server.stutino.support.docs.RestDocumentTest;

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

@WebMvcTest(NoticeController.class)
@DisplayName("NoticeControllerTest 에서")
class NoticeControllerTest extends RestDocumentTest {
    @MockBean private NoticeService noticeService;

    @Test
    @DisplayName("공지를 성공적으로 등록하는가?")
    void successRegisterNotice() throws Exception {
        // give
        NoticeRegisterRequest request =
                new NoticeRegisterRequest(
                        1L,
                        "공지사항 제목1",
                        "공지사항 내용1"
                );

        doNothing()
                .when(noticeService)
                .registerNotice(any());

        // when
        ResultActions perform =
                mockMvc.perform(
                        post("/notice")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toRequestBody(request)));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register notice",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("프로그램 별 공지사항 목록을 성공적으로 조회하는가?")
    void successGetAllByProgram() throws Exception {
        // given
        when(noticeService.getNoticeList(any()))
                .thenReturn(List.of(
                        new NoticeListResponse(
                                "공지사항 제목1",
                                "공지사항 내용1"
                        ),
                        new NoticeListResponse(
                                "공지사항 제목1",
                                "공지사항 내용1"
                        )
                ));

        // when
        ResultActions perform =
                mockMvc.perform(
                    get("/notice/{programId}", 1L));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("find all notice by program",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
