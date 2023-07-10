package server.stutino.domain.question.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.stutino.domain.question.dto.request.QuestionRegisterRequest;
import server.stutino.domain.question.dto.response.QuestionListResponse;
import server.stutino.domain.question.service.QuestionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionController {
    QuestionService questionService;
    
    /*
    * Q&A 등록
    * */
    @PostMapping
    public ResponseEntity<Void> registerQuestion(@Valid @RequestBody QuestionRegisterRequest request) {
        questionService.registerQuestion(request);
        return ResponseEntity.noContent().build();
    }
    
    /*
    * Q&A 목록 조회
    * */
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<QuestionListResponse>> getQuestionList(@PathVariable("programId") Long programId) {
        List<QuestionListResponse> response = questionService.getQuestionList(programId);
        return ResponseEntity.ok().body(response);
    }
}