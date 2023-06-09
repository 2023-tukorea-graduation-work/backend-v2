package tuk.mentor.domain.survey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuk.mentor.domain.program.repository.ProgramRepository;
import tuk.mentor.domain.survey.dto.request.SurveyRegisterRequest;
import tuk.mentor.domain.survey.dto.response.SurveyDefaultResponse;
import tuk.mentor.domain.survey.entity.ProgramReview;
import tuk.mentor.domain.survey.entity.ProgramSurveyAnswer;
import tuk.mentor.domain.survey.entity.Survey;
import tuk.mentor.domain.survey.entity.SurveyAnswer;
import tuk.mentor.domain.survey.repository.ProgramReviewRepository;
import tuk.mentor.domain.survey.repository.ProgramSurveyAnswerRepository;
import tuk.mentor.domain.survey.repository.SurveyAnswerRepository;
import tuk.mentor.domain.survey.repository.SurveyRepository;
import tuk.mentor.domain.user.mentee.entity.Mentee;
import tuk.mentor.domain.user.mentee.repository.MenteeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;
    private final ProgramSurveyAnswerRepository programSurveyAnswerRepository;
    private final ProgramReviewRepository programReviewRepository;
    private final ProgramRepository programRepository;
    private final MenteeRepository menteeRepository;

    /*
     * 설문 등록
     * */
    @Transactional
    public void registerSurvey(SurveyRegisterRequest request) {
        // [1] 설문지 질문 및 답변 목록 조회
        List<Survey> questions = surveyRepository.findAll();

        Mentee mentee = menteeRepository.findById(request.getUserId()).orElseThrow(EntityNotFoundException::new);

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

    /*
    * 설문조사 폼 조회
    * */
    public SurveyDefaultResponse getSurveyForm() {
        // [1] 설문조사 항목 조회
        List<Survey> questions = surveyRepository.findAll();
        List<SurveyAnswer> answers = surveyAnswerRepository.findAll();

        return SurveyDefaultResponse.builder()
                .answers(answers)
                .questions(questions)
                .build();
    }
}


