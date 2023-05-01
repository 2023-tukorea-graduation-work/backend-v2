package tuk.mentor.domain.survey.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tuk.mentor.domain.survey.dto.request.SurveyRegisterRequest;
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
}
