package server.stutino.domain.exam.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import server.stutino.domain.exam.dto.request.ExamRegisterRequest;
import server.stutino.domain.exam.entity.Exam;
import server.stutino.domain.exam.entity.MultipleChoiceOptions;
import server.stutino.domain.exam.entity.MultipleChoiceQuestion;
import server.stutino.domain.exam.entity.SubjectQuestion;
import server.stutino.domain.exam.repository.ExamRepository;
import server.stutino.domain.exam.repository.MultipleChoiceQuestionRepository;
import server.stutino.domain.exam.repository.SubjectQuestionRepository;
import server.stutino.domain.program.repository.ProgramRepository;
import server.stutino.util.CustomDateUtil;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExamService {

    ProgramRepository programRepository;
    ExamRepository examRepository;
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    SubjectQuestionRepository subjectQuestionRepository;
    CustomDateUtil customDateUtil;


    public void registerExam(ExamRegisterRequest request) {
        // [1] Exam 객체 생성
        Exam exam = Exam.builder()
                .program(programRepository.findById(request.getProgramId()).orElseThrow(EntityNotFoundException::new))
                .title(request.getExamTitle())
                .examStartTime(customDateUtil.convertStringToLocalDateTime(request.getExamStartTime()))
                .examFinishTime(customDateUtil.convertStringToLocalDateTime(request.getExamFinishTime()))
                .isExamRegistered(request.getIsExamRegistered())
                .build();

        // [2] 객관식 문항 & 주관식 문항
        List<MultipleChoiceQuestion> multipleChoices = new ArrayList<>();
        List<SubjectQuestion> subjectQuestions = new ArrayList<>();

        request.getExamQuestionRegisterRequest().forEach(question -> {
            // 객관식
            if(question.isMultipleChoiceType()) {
                MultipleChoiceQuestion multipleChoiceQuestion
                        = MultipleChoiceQuestion.builder()
                            .exam(exam)
                            .question(question.getQuestion())
                            .score(question.getScore())
                            .build();

                List<MultipleChoiceOptions> options = question.getOptions().stream().map(
                        option -> MultipleChoiceOptions.builder()
                                .multipleChoiceQuestion(multipleChoiceQuestion)
                                .choices(option.getChoices())
                                .isCorrect(option.getIsCorrect())
                                .build()).toList();

                multipleChoiceQuestion.setOptions(options);

                multipleChoices.add(multipleChoiceQuestion);
            }
            // 주관식
            else {
                SubjectQuestion subjectQuestion
                        = SubjectQuestion.builder()
                        .exam(exam)
                        .question(question.getQuestion())
                        .score(question.getScore())
                        .subjectAnswer(question.getSubjectAnswer())
                        .build();

                subjectQuestions.add(subjectQuestion);
            }
        });

        // [3] 객관식 & 주관식 & 시험 정보 저장
        multipleChoiceQuestionRepository.saveAll(multipleChoices);
        subjectQuestionRepository.saveAll(subjectQuestions);
    }
}
