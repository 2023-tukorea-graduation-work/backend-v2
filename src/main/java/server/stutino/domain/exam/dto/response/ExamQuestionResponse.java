package server.stutino.domain.exam.dto.response;

import lombok.Data;
import server.stutino.domain.exam.entity.QuestionType;

import java.util.List;

@Data
public class ExamQuestionResponse {
    private QuestionType questionType;
    private String question;
    private Integer score;
    private List<ExamQuestionOptionsResponse> options;
    private String subjectAnswer;

    public ExamQuestionResponse(QuestionType questionType, String question, Integer score, List<ExamQuestionOptionsResponse> options) {
        this.questionType = questionType;
        this.question = question;
        this.score = score;
        this.options = options;
    }
    public ExamQuestionResponse(QuestionType questionType, String question, Integer score, String subjectAnswer) {
        this.questionType = questionType;
        this.question = question;
        this.score = score;
        this.subjectAnswer = subjectAnswer;
    }
}
