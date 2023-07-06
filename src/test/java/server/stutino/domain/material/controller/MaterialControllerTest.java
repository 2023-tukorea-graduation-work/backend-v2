package server.stutino.domain.material.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import server.stutino.domain.matirial.controller.MaterialController;
import server.stutino.domain.matirial.service.MaterialService;
import server.stutino.support.docs.RestDocumentTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentRequest;
import static server.stutino.support.docs.ApiDocumentUtils.getDocumentResponse;

@WebMvcTest(MaterialController.class)
@DisplayName("MaterialController 에서")
class MaterialControllerTest extends RestDocumentTest {
    @MockBean private MaterialService materialService;

    @Test
    @DisplayName("프로그램 자료를 성공적으로 등록하는가?")
    void successRegisterMaterial() throws Exception {
        // give
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                MediaType.MULTIPART_FORM_DATA_VALUE, "Program Material Test.txt file".getBytes());

        doNothing()
                .when(materialService)
                .registerMaterial(any(), any());

        // when
        ResultActions perform =
                mockMvc.perform(
                        multipart("/material/{programId}", 1L)
                                .file(file));
        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(print())
                .andDo(document("register material",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

//    @Test
//    @DisplayName("등록된 자료를 성공적으로 다운로드 하는가?")
//    void successDownloadMaterial() throws Exception {
//        // Mock data
//        Long materialId = 1L;
//        String fileUrl = "~~.png";
//        byte[] fileBytes = "Dummy file content".getBytes();
//
//        // Mock materialService.getFilePath()
//        when(materialService.getFilePath(anyLong()))
//                .thenReturn(fileUrl);
//
//        // Mock URL.openStream()
//        InputStream inputStream = new ByteArrayInputStream(fileBytes);
//        URL url = new URL(fileUrl);
//        when(url.openStream())
//                .thenReturn(inputStream);
//
//        // Call the controller method
//        ResultActions perform = mockMvc.perform(
//                get("/material/download/{materialId}", materialId));
//
//        // Verify the response
//        perform.andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_PDF))
//                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"filename.pdf\""))
//                .andExpect((ResultMatcher) content().bytes(fileBytes))
//                .andExpect(status().isOk());
//
//        // docs
//        perform.andDo(print())
//                .andDo(document("register material",
//                        getDocumentRequest(),
//                        getDocumentResponse()));
//    }
}
