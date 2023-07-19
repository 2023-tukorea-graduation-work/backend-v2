package server.stutino.domain.exam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExamListResponse {
    private Long examId;
    private String examTitle;
    private LocalDateTime examStartTime;
    private LocalDateTime examFinishTime;
}
