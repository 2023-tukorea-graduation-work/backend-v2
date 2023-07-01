package server.stutino.domain.program.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import server.stutino.domain.program.dto.request.ProgramRegisterRequest;
import server.stutino.domain.program.dto.request.ProgramWeekRegisterRequest;
import server.stutino.domain.program.service.ProgramService;
import server.stutino.support.docs.RestDocumentTest;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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

//        when(programService.registerProgram(any()))

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
}
