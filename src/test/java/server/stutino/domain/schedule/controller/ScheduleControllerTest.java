package server.stutino.domain.schedule.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import server.stutino.domain.schedule.dto.request.ScheduleRegisterRequest;
import server.stutino.domain.schedule.dto.response.ScheduleListResponse;
import server.stutino.domain.schedule.service.ScheduleService;
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

@WebMvcTest(ScheduleController.class)
@DisplayName("ScheduleController 에서")
class ScheduleControllerTest extends RestDocumentTest {
    @MockBean private ScheduleService scheduleService;

    @Test
    @DisplayName("일정을 성공적으로 등록하는가?")
    void successRegisterSchedule() throws Exception {
        // give
        ScheduleRegisterRequest request =
                new ScheduleRegisterRequest(
                        1L,
                        "일정 내용1",
                        "2023-06-05 00:00:00",
                        "2023-06-05 00:00:01"
                );

        doNothing()
            .when(scheduleService)
            .registerSchedule(any());

        // when
        ResultActions perform =
                mockMvc.perform(
                        post("/schedule")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toRequestBody(request)));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register schedule",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("일정 목록을 성공적으로 조회하는가?")
    void successGetAllSchedule() throws Exception {
        // given
        when(scheduleService.getScheduleList(any()))
                .thenReturn(List.of(
                        new ScheduleListResponse(
                                "공지사항 제목1",
                                "2023-06-05 00:00:00",
                                "2023-06-05 00:00:01"
                        ),
                        new ScheduleListResponse(
                                "공지사항 제목2",
                                "2023-06-05 00:00:00",
                                "2023-06-05 00:00:01"
                        )
                ));

        // when
        ResultActions perform =
                mockMvc.perform(
                        get("/schedule/{memberId}", 1L));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("find all schedule",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
