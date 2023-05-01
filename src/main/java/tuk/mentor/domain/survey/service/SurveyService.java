package tuk.mentor.domain.survey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuk.mentor.domain.mentee.entity.Mentee;
import tuk.mentor.domain.mentee.repository.MenteeRepository;
import tuk.mentor.domain.program.repository.ProgramRepository;
import tuk.mentor.domain.survey.dto.request.SurveyRegisterRequest;
import tuk.mentor.domain.survey.entity.ProgramReview;
import tuk.mentor.domain.survey.entity.ProgramSurveyAnswer;
import tuk.mentor.domain.survey.entity.Survey;
import tuk.mentor.domain.survey.repository.ProgramReviewRepository;
import tuk.mentor.domain.survey.repository.ProgramSurveyAnswerRepository;
import tuk.mentor.domain.survey.repository.SurveyRepository;
import tuk.mentor.global.login.LoginInfo;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final ProgramSurveyAnswerRepository programSurveyAnswerRepository;
    private final ProgramReviewRepository programReviewRepository;
    private final ProgramRepository programRepository;
    private final MenteeRepository menteeRepository;

    /*
     * 설문 등록
     * */
    @Transactional
    public void registerSurvey(SurveyRegisterRequest request, HttpServletRequest servletRequest) {
        // [1] 설문지 질문 및 답변 목록 조회
        List<Survey> questions = surveyRepository.findAll();

        LoginInfo loginInfo = (LoginInfo) servletRequest.getSession().getAttribute("loginInfo");
        Mentee mentee = menteeRepository.findById(loginInfo.getUserID()).orElseThrow(EntityNotFoundException::new);

        // 답변의 개수가 설문지의 질문의 개수와 동일한지 검사가 필요함.
//        if(questions.size() != request.getAnswers().size()) {
//
//        }
        // [2] 프로그램 설문 조사 답변 등록
        for(int i=0; i<questions.size(); i++) {
            programSurveyAnswerRepository.save(ProgramSurveyAnswer.builder()
                    .mentee(mentee)
                    .program(programRepository.findById(request.getProgramId()).orElseThrow(EntityNotFoundException::new))
                    .surveyId(questions.get(i).getId())
                    .answerId(request.getAnswers().get(i))
                    .build());
        }

        // [3] 프로그램 후기 등록
        programReviewRepository.save(ProgramReview.builder()
                .comment(request.getComment())
                .review(request.getReview())
                .build());
    }
}


