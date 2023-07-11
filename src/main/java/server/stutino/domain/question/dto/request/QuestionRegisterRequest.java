package server.stutino.domain.question.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class QuestionRegisterRequest {
    @NotNull
    private Long menteeId;
    @NotNull
    private Long programId;
    @NotNull
    private String question;
}
