package tuk.mentor.domain.user.mentor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import tuk.mentor.domain.user.mentor.controller.MentorController;
import tuk.mentor.domain.user.mentor.dto.request.MentorRegisterRequest;
import tuk.mentor.domain.user.mentor.entity.Lesson;
import tuk.mentor.domain.user.mentor.entity.Major;
import tuk.mentor.domain.user.mentor.service.MentorService;
import tuk.mentor.support.docs.RestDocumentTest;

import java.io.FileInputStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tuk.mentor.support.docs.ApiDocumentUtils.getDocumentRequest;
import static tuk.mentor.support.docs.ApiDocumentUtils.getDocumentResponse;

@WebMvcTest(MentorController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("MentorController 에서")
public class MentorTest extends RestDocumentTest {
    @MockBean private MentorService mentorService;
    @MockBean private PasswordEncoder passwordEncoder;
    @Test
    @DisplayName("멘토를 성공적으로 등록하는가?")
    void successRegisterMentor() throws Exception {

        final String filePath = "src/test/resources/testImage/test.png";
        FileInputStream fileInputStream = new FileInputStream(filePath);

        // given
        MockMultipartFile imageFile
                = new MockMultipartFile("file", "test.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);

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
        String json = objectMapper.writeValueAsString(request);

        // when
        ResultActions perform =
                mockMvc.perform(
                        multipart("/mentor")
                                .file(imageFile)
                                .param("mentorRegisterRequest", json));
        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(
                        document("mentor",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }
}
