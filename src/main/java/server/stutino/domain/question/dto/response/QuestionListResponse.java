package server.stutino.domain.question.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class QuestionListResponse {
    private Long questionId;
    private String question;
    private String answer;
    private LocalDateTime answerCreatedAt;
    private LocalDateTime answerUpdatedAt;

    @QueryProjection
    public QuestionListResponse(Long questionId, String question, String answer, LocalDateTime answerCreatedAt, LocalDateTime answerUpdatedAt) {
        this.questionId = questionId;
        this.question = question;
        this.answer = answer;
        this.answerCreatedAt = answerCreatedAt;
        this.answerUpdatedAt = answerUpdatedAt;
    }
}
