package tuk.mentor.domain.program;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import tuk.mentor.domain.program.controller.ProgramController;
import tuk.mentor.domain.program.service.ProgramService;
import tuk.mentor.support.docs.RestDocumentTest;

@WebMvcTest(ProgramController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("ProgramController 에서")
public class ProgramTest extends RestDocumentTest {
    @MockBean private ProgramService programService;

    @Test
    @DisplayName("로그인을 성공적으로 수행하는가")
    void successLoginUser() throws Exception {
        // given
//        given(userService.login(any()))
//                .willReturn(LoginResponse.builder()
//                        .userId(1L)
//                        .build());

        // when
//        ResultActions perform =
//                mockMvc.perform(
//                        post("/user/login")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(toRequestBody(LoginRequestFixture.REQUEST1.toLoginRequest())));

        // then
//        perform.andExpect(status().isOk())
//                .andExpect(jsonPath("$.userId").value(1L));

        // docs
//        perform.andDo(print())
//                .andDo(
//                        document("user",
//                                getDocumentRequest(),
//                                getDocumentResponse()));
    }
}
