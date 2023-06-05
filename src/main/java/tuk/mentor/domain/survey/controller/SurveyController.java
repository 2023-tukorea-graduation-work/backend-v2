package tuk.mentor.domain.survey.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.domain.survey.dto.request.SurveyRegisterRequest;
import tuk.mentor.domain.survey.dto.response.SurveyDefaultResponse;
import tuk.mentor.domain.survey.service.SurveyService;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    /*
     * 설문조사 폼 조회
     * */
    @GetMapping
    public ResponseEntity<SurveyDefaultResponse> getSurveyForm() {
        SurveyDefaultResponse response = surveyService.getSurveyForm();
        return ResponseEntity.ok().body(response);
    }

    /*
    * 프로그램 별 설문조사 등록
    * */
    @PostMapping
    public ResponseEntity<Void> registerSurvey(@RequestBody SurveyRegisterRequest surveyRegisterRequest) {
        surveyService.registerSurvey(surveyRegisterRequest);
        return ResponseEntity.ok().build();
    }
}
