package server.stutino.domain.question.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.stutino.domain.program.entity.Participants;
import server.stutino.domain.program.repository.ParticipantsRepository;
import server.stutino.domain.question.dto.request.QuestionRegisterRequest;
import server.stutino.domain.question.dto.response.QuestionListResponse;
import server.stutino.domain.question.entity.Question;
import server.stutino.domain.question.mapper.QuestionMapper;
import server.stutino.domain.question.repository.QuestionRepository;
import server.stutino.util.CustomStringUtil;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionService {

    QuestionRepository questionRepository;
    ParticipantsRepository participantsRepository;
    QuestionMapper questionMapper;

    /*
    *  질문 등록
    * */
    @Transactional
    public void registerQuestion(QuestionRegisterRequest request) {
        // [1] Question 기본 정보 저장
        System.out.println(CustomStringUtil.toString(request));

        Participants mentee = participantsRepository.findById(request.getMenteeId())
                .orElseThrow(EntityNotFoundException::new);

        System.out.println(mentee);

        questionRepository.save(Question.builder()
                .participants(mentee)
                .questions(request.getQuestion())
                .build());
    }

    public List<QuestionListResponse> getQuestionList(Long programId) {
        return questionRepository.getAllQuestionByProgramId(programId);
    }
}
