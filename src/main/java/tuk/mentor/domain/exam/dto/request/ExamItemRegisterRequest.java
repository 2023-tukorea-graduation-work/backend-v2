package tuk.mentor.domain.exam.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ExamItemRegisterRequest {
    private Integer choiceNum;
    private String choice;
    private Boolean isCorrect;
}
