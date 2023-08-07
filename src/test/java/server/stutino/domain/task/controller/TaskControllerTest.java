package server.stutino.domain.task.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;
import server.stutino.domain.task.dto.request.TaskRegisterRequest;
import server.stutino.domain.task.dto.response.TaskListResponse;
import server.stutino.domain.task.service.TaskService;
import server.stutino.support.docs.RestDocumentTest;

import java.time.LocalDateTime;
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

@WebMvcTest(TaskController.class)
@DisplayName("TaskController 에서")
class TaskControllerTest extends RestDocumentTest {
    @MockBean private TaskService taskService;

    @Test
    @DisplayName("과제 정보를 성공적으로 등록하는가?")
    void successRegisterTask() throws Exception {
        // give
        TaskRegisterRequest request = new TaskRegisterRequest(
                1L,
                "과제 제목1",
                "<div><input type='file'/><input type='text' /></div>",
                "2023-09-01 00:00:00",
                "2023-09-01 00:00:00"
        );

        List<MultipartFile> files =
                List.of(
                        new MockMultipartFile("file", "test.txt",
                                MediaType.MULTIPART_FORM_DATA_VALUE, "Task Material Test.txt file".getBytes()),
                        new MockMultipartFile("file", "test.txt",
                                MediaType.MULTIPART_FORM_DATA_VALUE, "Task Material Test.txt file".getBytes()),
                        new MockMultipartFile("file", "test.txt",
                                MediaType.MULTIPART_FORM_DATA_VALUE, "Task Material Test.txt file".getBytes())
                );

        doNothing()
                .when(taskService)
                .registerTask(any(), any());

        MockPart mockJsonPart = new MockPart("data", toRequestBody(request).getBytes());
        mockJsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // when
        MockMultipartHttpServletRequestBuilder requestBuilder = multipart("/task");
        for (MultipartFile file : files) {
            requestBuilder.file((MockMultipartFile) file);
        }

        ResultActions perform =
                mockMvc.perform(
                        requestBuilder.part(mockJsonPart)
                );


        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register task",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("프로그램 과제 목록을 성공적으로 조회하는가?")
    void successGetAllMaterial() throws Exception {
        // given
        when(taskService.findAllTask(any()))
                .thenReturn(List.of(
                        new TaskListResponse(
                                1L,
                                "과제 제목1",
                                "<div></div>",
                                LocalDateTime.now(),
                                LocalDateTime.now()
                        ),
                        new TaskListResponse(
                                1L,
                                "과제 제목2",
                                "<div></div>",
                                LocalDateTime.now(),
                                LocalDateTime.now()
                        )
                ));

        // when
        ResultActions perform =
                mockMvc.perform(
                        get("/task/program/{programId}", 1L));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("find all task by program",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
