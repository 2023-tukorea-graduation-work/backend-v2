package server.stutino.domain.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TaskListResponse {
    private Long taskId;
    private String title;
    private String content;
    private LocalDate startTaskDate;
    private LocalDate endTaskDate;

}
