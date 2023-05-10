package tuk.mentor.domain.exam.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tuk.mentor.domain.exam.entity.ExamQuestionType;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ExamQuestionRegisterRequest {
    private ExamQuestionType examQuestionType;
    private String examQuestion;
    private List<ExamItemRegisterRequest> items;
    private String essayAnswer;
    private Integer score;
}
