package server.stutino.domain.material.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.ResultActions;
import server.stutino.domain.matirial.controller.MaterialController;
import server.stutino.domain.matirial.dto.request.MaterialRegisterRequest;
import server.stutino.domain.matirial.dto.response.MaterialDetailResponse;
import server.stutino.domain.matirial.dto.response.MaterialListResponse;
import server.stutino.domain.matirial.service.MaterialService;
import server.stutino.support.docs.RestDocumentTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
        MaterialRegisterRequest request = new MaterialRegisterRequest(
                1l,
                "프로그램 자료 제목1",
                "프로그램 자료 상세내용1"
        );

        // give
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                MediaType.MULTIPART_FORM_DATA_VALUE, "Program Material Test.txt file".getBytes());

        doNothing()
                .when(materialService)
                .registerMaterial(any(), any());

        MockPart mockJsonPart = new MockPart("data", toRequestBody(request).getBytes());
        mockJsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform =
                mockMvc.perform(
                        multipart("/material")
                                .file(file)
                                .part(mockJsonPart));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register material",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("프로그램 자료 목록을 성공적으로 조회하는가?")
    void successGetAllMaterial() throws Exception {
        // given
        when(materialService.findAllMaterials(any()))
                .thenReturn(List.of(
                        new MaterialListResponse(
                                1L,
                                "자료 제목1",
                                "상세 내용1",
                                "fileName1",
                                "filepath"
                        ),
                        new MaterialListResponse(
                                2L,
                                "자료 제목2",
                                "상세 내용2",
                                "fileName2",
                                "filepath"
                        )
                ));

        // when
        ResultActions perform =
                mockMvc.perform(
                        get("/material/program/{programId}", 1L));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("find all material by program",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("프로그램 자료 상세 정보를 성공적으로 조회하는가?")
    void successGetMaterialById() throws Exception {
        // given
        when(materialService.findMaterialById(any()))
                .thenReturn(new MaterialDetailResponse(
                                2L,
                                "자료 제목2",
                                "상세 내용2",
                                "fileName2"
                        ));

        // when
        ResultActions perform =
                mockMvc.perform(
                        get("/material/{materialId}", 1L));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("find material by materialId",
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
