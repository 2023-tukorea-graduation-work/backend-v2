package server.stutino.domain.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskListResponse {
    private Long taskId;
    private String title;
    private String content;
    private LocalDateTime startTaskDateTime;
    private LocalDateTime endTaskDateTime;
}
