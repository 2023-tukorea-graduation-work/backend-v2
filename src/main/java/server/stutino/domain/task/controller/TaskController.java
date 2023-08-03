package server.stutino.domain.task.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.stutino.domain.task.dto.request.TaskRegisterRequest;
import server.stutino.domain.task.service.TaskService;

import javax.validation.Valid;

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
    public ResponseEntity<Void> registerExam(@Valid @RequestBody TaskRegisterRequest taskRegisterRequest) {
        taskService.registerTask(taskRegisterRequest);
        return ResponseEntity.ok().build();
    }

    /*
    * 과제 목록 조회
    * */
//    @GetMapping("/program/{programId}")
//    public ResponseEntity<List<ExamListResponse>> findAllExam(@PathVariable("programId") Long programId) {
//        return ResponseEntity.ok(taskService.findAllTask(programId));
//    }
}