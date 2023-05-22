package tuk.mentor.domain.exam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.domain.exam.dto.request.ExamRegisterRequest;
import tuk.mentor.domain.exam.entity.Exam;
import tuk.mentor.domain.exam.service.ExamService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;
    @PostMapping("/{programId}")
    public ResponseEntity<Void> registerExam(@PathVariable("programId") Long programId,
                                                                 @RequestBody ExamRegisterRequest examRegisterRequest,
                                                                 HttpServletRequest servletRequest) {
        examService.registerExam(programId, examRegisterRequest, servletRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{programId}")
    public ResponseEntity<Exam> getExam(@PathVariable("programId") Long programId) {
        Exam response = examService.getExam(programId);
        return ResponseEntity.ok().body(response);
    }
}