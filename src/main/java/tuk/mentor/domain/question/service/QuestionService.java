package tuk.mentor.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuk.mentor.domain.program.repository.ProgramRepository;
import tuk.mentor.domain.question.dto.request.QuestionRegisterRequest;
import tuk.mentor.domain.question.dto.response.QuestionListResponse;
import tuk.mentor.domain.question.entity.Question;
import tuk.mentor.domain.question.mapper.QuestionMapper;
import tuk.mentor.domain.question.repository.QuestionRepository;
import tuk.mentor.domain.user.mentee.entity.Mentee;
import tuk.mentor.domain.user.mentee.repository.MenteeRepository;
import tuk.mentor.login.LoginInfo;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MenteeRepository menteeRepository;
    private final ProgramRepository programRepository;
    private final QuestionMapper questionMapper;

    /*
    *  질문 등록
    * */
    @Transactional
    public void registerQuestion(QuestionRegisterRequest request, HttpServletRequest httpServletRequest) {
        // [1] Qustion 기본 정보 저장
        HttpSession session = httpServletRequest.getSession();
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Mentee mentee = menteeRepository.findById(loginInfo.getUserID()).orElseThrow(EntityNotFoundException::new);

        questionRepository.save(Question.builder()
                .mentee(mentee)
                .program(programRepository.findById(request.getProgramId()).orElseThrow(EntityNotFoundException::new))
                .question(request.getQuestion())
                .build());
    }

    public List<QuestionListResponse> getQuestionList(Long programId) {
        List<Question> questions = questionRepository.getAllQuestionByProgramId(programId);
        return questions.stream().map(questionMapper::toQuestionListDto).toList();
    }
}
