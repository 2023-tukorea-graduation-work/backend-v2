package server.stutino.domain.question.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class QuestionRegisterRequest {
    private Long menteeId;
    private Long programId;
    private String question;
}
