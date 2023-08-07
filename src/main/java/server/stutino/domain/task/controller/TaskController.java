package server.stutino.domain.task.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.stutino.domain.task.dto.request.TaskRegisterRequest;
import server.stutino.domain.task.dto.response.TaskListResponse;
import server.stutino.domain.task.service.TaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {

    TaskService taskService;

    /*
    * 과제 등록(멘토접근)
    * */
    @PostMapping
    public ResponseEntity<Void> registerTask(
            @RequestPart(value = "data", required = true) @Valid TaskRegisterRequest taskRegisterRequest,
            @RequestPart(value = "files", required = false) @Valid List<MultipartFile> files) {
        taskService.registerTask(taskRegisterRequest, files);
        return ResponseEntity.ok().build();
    }

    /*
    * 과제 목록 조회
    * */
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<TaskListResponse>> findAllExam(@PathVariable("programId") Long programId) {
        return ResponseEntity.ok(taskService.findAllTask(programId));
    }
}