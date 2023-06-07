package tuk.mentor.domain.user.mentor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import tuk.mentor.domain.user.mentor.controller.MentorController;
import tuk.mentor.domain.user.mentor.service.MentorService;
import tuk.mentor.support.docs.RestDocumentTest;

@WebMvcTest(MentorController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("MentorController 에서")
public class MentorTest extends RestDocumentTest {
    @MockBean private MentorService mentorService;
    @Test
    @DisplayName("멘토를 성공적으로 등록하는가?")
    void successRegisterMentor() throws Exception {
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
