package server.stutino.domain.exam.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.stutino.domain.exam.entity.QuestionType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExamQuestionRegisterRequest {
    @NotNull
    private QuestionType questionType;
    @NotBlank
    private String question;
    @NotNull
    private Integer score;
    private List<ExamQuestionOptionsRegisterRequest> options;
    private String subjectAnswer;

    public ExamQuestionRegisterRequest(QuestionType questionType, String question, Integer score, List<ExamQuestionOptionsRegisterRequest> options) {
        this.questionType = questionType;
        this.question = question;
        this.score = score;
        this.options = options;
    }
    public ExamQuestionRegisterRequest(QuestionType questionType, String question, Integer score, String subjectAnswer) {
        this.questionType = questionType;
        this.question = question;
        this.score = score;
        this.subjectAnswer = subjectAnswer;
    }
}
