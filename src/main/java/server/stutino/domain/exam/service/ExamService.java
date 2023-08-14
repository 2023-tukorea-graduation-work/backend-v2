package server.stutino.domain.exam.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.stutino.domain.exam.dto.request.ExamQuestionOptionsRegisterRequest;
import server.stutino.domain.exam.dto.request.ExamQuestionRegisterRequest;
import server.stutino.domain.exam.dto.request.ExamRegisterRequest;
import server.stutino.domain.exam.dto.response.ExamDetailResponse;
import server.stutino.domain.exam.dto.response.ExamListResponse;
import server.stutino.domain.exam.dto.response.ExamQuestionOptionsResponse;
import server.stutino.domain.exam.dto.response.ExamQuestionResponse;
import server.stutino.domain.exam.entity.*;
import server.stutino.domain.exam.repository.ExamRepository;
import server.stutino.domain.exam.repository.MultipleChoiceOptionsRepository;
import server.stutino.domain.exam.repository.MultipleChoiceQuestionRepository;
import server.stutino.domain.exam.repository.SubjectQuestionRepository;
import server.stutino.domain.program.entity.Program;
import server.stutino.domain.program.repository.ProgramRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExamService {

    ProgramRepository programRepository;
    ExamRepository examRepository;
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    MultipleChoiceOptionsRepository multipleChoiceOptionsRepository;
    SubjectQuestionRepository subjectQuestionRepository;

    @Transactional
    public void registerExam(ExamRegisterRequest request) {

        Program program = programRepository.findById(request.getProgramId())
                .orElseThrow(EntityNotFoundException::new);

        Exam exam = Exam.builder()
                .title(request.getExamTitle())
                .program(program)
                .examStartTime(LocalDateTime.parse(request.getExamStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .examFinishTime(LocalDateTime.parse(request.getExamFinishTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .isExamRegistered(request.getIsExamRegistered())
                .build();

        examRepository.save(exam);

        List<SubjectQuestion> subjectQuestions = new ArrayList<>();
        List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();

        for(ExamQuestionRegisterRequest question : request.getExamQuestionRegisterRequest()) {
            if(question.getQuestionType().equals(QuestionType.SUBJECT_QUESTION)) {
                subjectQuestions.add(SubjectQuestion.builder()
                        .exam(exam)
                        .question(question.getQuestion())
                        .score(question.getScore())
                        .subjectAnswer(question.getSubjectAnswer())
                        .build());
            }
            else {
                List<MultipleChoiceOptions> options = new ArrayList<>();
                for(ExamQuestionOptionsRegisterRequest option : question.getOptions()) {
                    options.add(MultipleChoiceOptions.builder()
                            .choices(option.getChoices())
                            .isCorrect(option.getIsCorrect())
                            .build());
                }

                multipleChoiceQuestions.add(MultipleChoiceQuestion.builder()
                        .exam(exam)
                        .question(question.getQuestion())
                        .score(question.getScore())
                        .multipleChoiceOptions(options)
                        .build());
            }
        }

        subjectQuestionRepository.saveAll(subjectQuestions);
        multipleChoiceQuestionRepository.saveAll(multipleChoiceQuestions);
    }

    /*
    * 시험 목록 조회
    * */
    public List<ExamListResponse> findAllExam(Long programId) {
        return examRepository.findAllExam(programId).stream().map(exam ->
                new ExamListResponse(
                        exam.getId(),
                        exam.getTitle(),
                        exam.getExamStartTime(),
                        exam.getExamFinishTime()
                )).toList();
    }

    /*
    * 시험 상세 정보 조회
    * */
    public ExamDetailResponse findExamById(Long examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(EntityNotFoundException::new);

        List<ExamQuestionResponse> questions = new ArrayList<>();

        multipleChoiceQuestionRepository.findAllMultiQuestionByExamId(examId)
                .stream().map(mcq -> questions.add(new ExamQuestionResponse(
                        QuestionType.MULTIPLE_CHOICE_QUESTION,
                        mcq.getQuestion(),
                        mcq.getScore(),
                        multipleChoiceOptionsRepository.findMultipleChoiceOptionsByQuestionId(mcq.getId())
                                .stream().map(option ->
                                        new ExamQuestionOptionsResponse(
                                                option.getChoices(),
                                                option.isCorrect()
                                        )).toList())
                        )
                );

        subjectQuestionRepository.findSubjectQuestionByExamId(examId)
                .stream().map(sq -> questions.add(new ExamQuestionResponse(
                        QuestionType.SUBJECT_QUESTION,
                        sq.getQuestion(),
                        sq.getScore(),
                        sq.getSubjectAnswer()
                )));

        return new ExamDetailResponse(
                examId,
                exam.getTitle(),
                exam.getExamStartTime(),
                exam.getExamFinishTime(),
                questions
        );
    }
}
