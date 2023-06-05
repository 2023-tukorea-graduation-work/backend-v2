package tuk.mentor.domain.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.domain.question.dto.request.QuestionRegisterRequest;
import tuk.mentor.domain.question.dto.response.QuestionListResponse;
import tuk.mentor.domain.question.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    
    /*
    * 프로그램 별 Q&A 등록
    * */
    @PostMapping
    public ResponseEntity<Void> registerQuestion(@RequestBody QuestionRegisterRequest request) {
        questionService.registerQuestion(request);
        return ResponseEntity.ok().build();
    }
    
    /*
    * 프로그램 별 Q&A 목록 조회
    * */
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<QuestionListResponse>> getQuestionList(@PathVariable("programId") Long programId) {
        List<QuestionListResponse> response = questionService.getQuestionList(programId);
        return ResponseEntity.ok().body(response);
    }
}