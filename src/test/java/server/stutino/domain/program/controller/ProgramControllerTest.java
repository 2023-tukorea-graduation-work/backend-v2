package server.stutino.domain.program.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import server.stutino.domain.member.dto.request.MentorRegisterRequest;
import server.stutino.domain.member.entity.Lesson;
import server.stutino.domain.member.entity.Major;
import server.stutino.domain.program.service.ProgramService;
import server.stutino.support.docs.RestDocumentTest;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentRequest;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentResponse;

@WebMvcTest(ProgramController.class)
@DisplayName("ProgramController 에서")
class ProgramControllerTest extends RestDocumentTest {
    @MockBean private ProgramService programService;
    @Test
    @DisplayName("프로햣그램을 성공적으로 등록하는가?")
    void successRegisterProgram() throws Exception {
        // given


        // when

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register program",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
