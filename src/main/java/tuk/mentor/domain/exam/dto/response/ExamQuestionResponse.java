package tuk.mentor.domain.exam.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tuk.mentor.domain.exam.dto.request.ExamItemRegisterRequest;
import tuk.mentor.domain.exam.entity.ExamQuestionType;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ExamQuestionResponse {
    private ExamQuestionType examQuestionType;
    private String examQuestion;
    private List<ExamItemRegisterRequest> items;
    private String essayAnswer;
    private Integer score;
}
