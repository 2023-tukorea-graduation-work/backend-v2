package server.stutino.domain.exam.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExamRegisterRequest {
    private Long programId;
    private String examTitle;
    private String examStartTime;
    private String examFinishTime;
    private Boolean isExamRegistered;
    private List<ExamQuestionRegisterRequest> examQuestionRegisterRequest;
}
