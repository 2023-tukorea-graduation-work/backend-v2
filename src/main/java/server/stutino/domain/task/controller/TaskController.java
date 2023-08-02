package server.stutino.domain.task.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.stutino.domain.exam.dto.request.ExamRegisterRequest;
import server.stutino.domain.exam.dto.response.ExamDetailResponse;
import server.stutino.domain.exam.dto.response.ExamListResponse;
import server.stutino.domain.exam.service.ExamService;
import server.stutino.domain.task.service.TaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {

    TaskService taskService;

/*    *//*
    * 과제 등록(멘토접근)
    * *//*
    @PostMapping
    public ResponseEntity<Void> registerExam(@Valid @RequestBody ) {
        taskService.registerTask();
        return ResponseEntity.ok().build();
    }

    *//*
    * 과제 목록 조회
    * *//*
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<ExamListResponse>> findAllExam(@PathVariable("programId") Long programId) {
        return ResponseEntity.ok(taskService.findAllTask(programId));
    }*/
}