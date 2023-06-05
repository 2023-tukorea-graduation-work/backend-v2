package tuk.mentor.domain.exam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.domain.exam.dto.request.ExamRegisterRequest;
import tuk.mentor.domain.exam.entity.Exam;
import tuk.mentor.domain.exam.service.ExamService;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;
    
    /*
    * 프로그램 별 시험문제 등록
    * */
    @PostMapping("/program/{programId}")
    public ResponseEntity<Void> registerExam(@PathVariable("programId") Long programId,
                                                                 @RequestBody ExamRegisterRequest examRegisterRequest) {
        examService.registerExam(programId, examRegisterRequest);
        return ResponseEntity.ok().build();
    }

    /*
    * 프로그램 별 시험 문제 조회
    * */
    @GetMapping("/program/{programId}")
    public ResponseEntity<Exam> getExam(@PathVariable("programId") Long programId) {
        Exam response = examService.getExam(programId);
        return ResponseEntity.ok().body(response);
    }
}