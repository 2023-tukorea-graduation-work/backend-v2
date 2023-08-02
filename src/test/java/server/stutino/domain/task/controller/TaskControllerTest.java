package server.stutino.domain.task.controller;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import server.stutino.domain.exam.controller.ExamController;
import server.stutino.domain.exam.service.ExamService;
import server.stutino.support.docs.RestDocumentTest;

@WebMvcTest(TaskController.class)
@DisplayName("TaskController 에서")
class TaskControllerTest extends RestDocumentTest {
    @MockBean private ExamService examService;


}
