package tuk.mentor.domain.exam.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tuk.mentor.domain.exam.entity.ExamRegisterType;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ExamRegisterRequest {
    private String examStartTime;
    private String examFinishTime;
    private String examTitle;
    private ExamRegisterType examRegisterType;
    private List<ExamQuestionRegisterRequest> questions;
}
