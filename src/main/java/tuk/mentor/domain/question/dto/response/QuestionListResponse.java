package tuk.mentor.domain.question.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class QuestionListResponse {
    private Long questionId;
    private String question;
    private String answer;
    private LocalDateTime answerCreatedAt;
    private LocalDateTime answerUpdatedAt;
}
