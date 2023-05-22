package tuk.mentor.domain.exam.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tuk.mentor.domain.exam.entity.ExamRegisterType;

import java.util.List;

@Getter
@Setter
@Builder
public class ExamResponse {
    private String examStartTime;
    private String examFinishTime;
    private String examTitle;
    private ExamRegisterType examRegisterType;
    private List<ExamQuestionResponse> questions;
}
