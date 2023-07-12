package server.stutino.domain.exam.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExamQuestionOptionsRegisterRequest {
    @NotNull
    private String choices;
    @NotNull
    private Boolean isCorrect;
}
