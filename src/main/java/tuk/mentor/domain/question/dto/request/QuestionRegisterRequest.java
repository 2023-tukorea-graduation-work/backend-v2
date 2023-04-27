package tuk.mentor.domain.question.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class QuestionRegisterRequest {
    private Long programId;
    private String question;
}
