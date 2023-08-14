package server.stutino.domain.exam.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import server.stutino.domain.program.repository.ProgramRepository;
import server.stutino.util.CustomDateUtil;

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
    CustomDateUtil customDateUtil;

    @Transactional
    public void registerExam(ExamRegisterRequest request) {
        // [1] Exam 객체 생성
        Exam exam = examRepository.save(Exam.builder()
                .program(programRepository.findById(request.getProgramId()).orElseThrow(EntityNotFoundException::new))
                .title(request.getExamTitle())
                .examStartTime(LocalDateTime.parse(request.getExamStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .examFinishTime(LocalDateTime.parse(request.getExamFinishTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .isExamRegistered(request.getIsExamRegistered())
                .build());

        // [2] 객관식 문항 & 주관식 문항
        request.getExamQuestionRegisterRequest().forEach(question -> {
            // 객관식
            if(question.getQuestion() != null) {
                MultipleChoiceQuestion multipleChoiceQuestion
                        = multipleChoiceQuestionRepository.save(MultipleChoiceQuestion.builder()
                        .exam(exam)
                        .question(question.getQuestion())
                        .score(question.getScore())
                        .build());

                question.getOptions().stream().map(
                        option -> multipleChoiceOptionsRepository.save(MultipleChoiceOptions.builder()
                                .multipleChoiceQuestion(multipleChoiceQuestion)
                                .choices(option.getChoices())
                                .isCorrect(option.getIsCorrect())
                                .build()));
            }
            // 주관식
            else {
                subjectQuestionRepository.save(
                        SubjectQuestion.builder()
                                .exam(exam)
                                .question(question.getQuestion())
                                .score(question.getScore())
                                .subjectAnswer(question.getSubjectAnswer())
                                .build()
                );
            }
        });
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
