package server.stutino.domain.exam.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.stutino.domain.exam.entity.QuestionType;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExamQuestionRegisterRequest {
    @NotNull
    private QuestionType questionType;
    @NotNull
    private Integer score;

    public boolean isMultipleChoiceType() {
        return this.questionType.equals(QuestionType.MULTIPLE_CHOICE_QUESTION);
    }
}
