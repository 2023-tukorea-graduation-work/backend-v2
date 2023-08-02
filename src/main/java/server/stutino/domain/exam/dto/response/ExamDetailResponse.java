package server.stutino.domain.exam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ExamDetailResponse {
    private Long examId;
    private String examTitle;
    private LocalDateTime examStartTime;
    private LocalDateTime examFinishTime;
    private List<ExamQuestionResponse> examQuestionRegisterResponse;

}
