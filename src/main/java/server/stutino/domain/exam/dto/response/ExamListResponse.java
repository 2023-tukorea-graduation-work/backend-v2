package server.stutino.domain.exam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExamListResponse {
    private Long examId;
    private String examTitle;
    private LocalDate examStartTime;
    private LocalDate examFinishTime;
}
