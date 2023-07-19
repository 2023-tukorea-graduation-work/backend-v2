package server.stutino.domain.exam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExamQuestionOptionsResponse {
    private String choices;
    private Boolean isCorrect;
}
