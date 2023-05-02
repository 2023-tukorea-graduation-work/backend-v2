package tuk.mentor.domain.survey.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.domain.survey.dto.request.SurveyRegisterRequest;
import tuk.mentor.domain.survey.dto.response.SurveyDefaultResponse;
import tuk.mentor.domain.survey.service.SurveyService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @PostMapping("/program")
    public ResponseEntity<Void> registerSurvey(@RequestBody SurveyRegisterRequest surveyRegisterRequest,
                                               HttpServletRequest servletRequest) {
        surveyService.registerSurvey(surveyRegisterRequest, servletRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<SurveyDefaultResponse> getSurveyForm() {
        SurveyDefaultResponse response = surveyService.getSurveyForm();
        return ResponseEntity.ok().body(response);
    }

}
