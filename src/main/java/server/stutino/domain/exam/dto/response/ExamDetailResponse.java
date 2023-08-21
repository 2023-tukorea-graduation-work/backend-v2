package server.stutino.domain.exam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ExamDetailResponse {
    private Long examId;
    private String examTitle;
    private LocalDate examStartTime;
    private LocalDate examFinishTime;
    private List<ExamQuestionResponse> examQuestionRegisterResponse;

}
