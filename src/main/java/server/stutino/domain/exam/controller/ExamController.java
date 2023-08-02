package server.stutino.domain.exam.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.stutino.domain.exam.dto.request.ExamRegisterRequest;
import server.stutino.domain.exam.dto.response.ExamDetailResponse;
import server.stutino.domain.exam.dto.response.ExamListResponse;
import server.stutino.domain.exam.service.ExamService;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<Void> registerExam(@Valid @RequestBody ExamRegisterRequest examRegisterRequest) {
        examService.registerExam(examRegisterRequest);
        return ResponseEntity.ok().build();
    }

    /*
    * 시험 목록 조회
    * */
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<ExamListResponse>> findAllExam(@PathVariable("programId") Long programId) {
        return ResponseEntity.ok(examService.findAllExam(programId));
    }

    /*
     * 시험 상세 조회(mentor 접근)
     * */
    @GetMapping("/{examId}")
    public ResponseEntity<ExamDetailResponse> findExamById(@PathVariable("examId") Long examId) {
        return ResponseEntity.ok(examService.findExamById(examId));
    }

}