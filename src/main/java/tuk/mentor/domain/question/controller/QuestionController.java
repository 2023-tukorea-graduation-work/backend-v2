package tuk.mentor.domain.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.domain.question.dto.request.QuestionRegisterRequest;
import tuk.mentor.domain.question.dto.response.QuestionListResponse;
import tuk.mentor.domain.question.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    @PostMapping
    public ResponseEntity<Void> registerQuestion(@RequestBody QuestionRegisterRequest request,
                                               HttpServletRequest servletRequest) {
        questionService.registerQuestion(request, servletRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{programId}")
    public ResponseEntity<List<QuestionListResponse>> getQuestionList(@PathVariable("programId") Long programId) {
        List<QuestionListResponse> response = questionService.getQuestionList(programId);
        return ResponseEntity.ok().body(response);
    }
}