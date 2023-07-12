package server.stutino.domain.exam.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.stutino.domain.exam.dto.request.ExamRegisterRequest;
import server.stutino.domain.exam.service.ExamService;

import javax.validation.Valid;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExamController {

    ExamService examService;

    /*
    * 프로그램 별 시험 등록
    * */
    @PostMapping
    public ResponseEntity<Void> registerExam(@Valid @RequestBody  ExamRegisterRequest examRegisterRequest) {
        examService.registerExam(examRegisterRequest);
        return ResponseEntity.ok().build();
    }

    /*
    *
    * */
}