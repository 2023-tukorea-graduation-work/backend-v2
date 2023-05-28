package tuk.mentor.domain.exam.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuk.mentor.domain.exam.dto.request.ExamItemRegisterRequest;
import tuk.mentor.domain.exam.dto.request.ExamQuestionRegisterRequest;
import tuk.mentor.domain.exam.dto.request.ExamRegisterRequest;
import tuk.mentor.domain.exam.entity.Exam;
import tuk.mentor.domain.exam.entity.ExamItem;
import tuk.mentor.domain.exam.entity.ExamQuestion;
import tuk.mentor.domain.exam.entity.ExamQuestionType;
import tuk.mentor.domain.exam.repository.ExamItemRepository;
import tuk.mentor.domain.exam.repository.ExamQuestionRepository;
import tuk.mentor.domain.exam.repository.ExamRepository;
import tuk.mentor.domain.program.repository.ProgramRepository;
import tuk.mentor.domain.user.mentor.entity.Mentor;
import tuk.mentor.domain.user.mentor.repository.MentorRepository;
import tuk.mentor.util.DateUtil;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ExamService {
    private final MentorRepository mentorRepository;
    private final ProgramRepository programRepository;
    private final ExamRepository examRepository;
    private final ExamQuestionRepository examQuestionRepository;
    private final ExamItemRepository examItemRepository;
    private final DateUtil dateUtil;

    @Transactional
    public void registerExam(Long programId, ExamRegisterRequest request) {
        // [1] 멘토 정보 조회
        Mentor mentor = mentorRepository.findById(request.getUserId()).orElseThrow(EntityNotFoundException::new);

        // [2] Exam build
        Exam exam = Exam.builder()
                .examStartTime(dateUtil.convertStringToLocalDateTime(request.getExamStartTime()))
                .examFinishTime(dateUtil.convertStringToLocalDateTime(request.getExamFinishTime()))
                .mentor(mentor)
                .program(programRepository.findById(programId).orElseThrow(EntityNotFoundException::new))
                .title(request.getExamTitle())
                .examRegisterType(request.getExamRegisterType())
                .build();

        examRepository.save(exam);

        // [3] Exam Question build
        List<ExamQuestion> questions = request.getQuestions().stream().map(
                question -> ExamQuestion.builder()
                    .question(question.getExamQuestion())
                    .examQuestionType(question.getExamQuestionType())
                    .score(question.getScore())
                    .exam(exam)
                    .build())
                .toList();

        // [3-1] Exam Qeustion 중 Correct Answer Mapping
        int questionIdx = 0;
        for(ExamQuestionRegisterRequest examQuestion : request.getQuestions()) {
            if(ExamQuestionType.ESSAY_QUESTION.equals(examQuestion.getExamQuestionType())) {
                questions.get(questionIdx++).setCorrectAnswer(examQuestion.getEssayAnswer());
            }
            else {
                List<ExamItem> items = new ArrayList<ExamItem>();
                for(ExamItemRegisterRequest item : examQuestion.getItems()) {
                    if(item.getIsCorrect()) {
                        questions.get(questionIdx).setCorrectAnswer(String.valueOf(item.getChoiceNum()));
                    }
                    items.add(ExamItem.builder()
                            .choice(item.getChoice())
                            .choiceNum(item.getChoiceNum())
                            .examQuestion(questions.get(questionIdx))
                            .build());
                }
                questionIdx++;
                // [3-2] Exam Question Item Save
                examItemRepository.saveAll(items);
            }
        }

        // [4] Exam Question Save
        examQuestionRepository.saveAll(questions);
    }

    public Exam getExam(Long programId) {
        // [1] Exam 조회
        Exam exam = examRepository.findExamByProgramId(programId);

        // [2] Exam Question 조회
        List<ExamQuestion> examQuestions = examQuestionRepository.findExamQuestionByExamId(exam.getId());

        // [3] Exam Item 조회
        for(ExamQuestion examQuestion : examQuestions) {
            if(ExamQuestionType.MULTI_CHOICE_QUESTION.equals(examQuestion.getExamQuestionType())) {
                examQuestion.setExamItems(
                        examItemRepository.findExamItemByExamQuestionId(examQuestion.getId())
                );
            }
        }

        exam.setExamQuestions(examQuestions);
        return exam;
    }
}


