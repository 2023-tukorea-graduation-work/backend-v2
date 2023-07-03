package server.stutino.domain.schedule.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
public class ScheduleListResponse {
    private String content;
    private String scheduleStartDatetime;
    private String scheduleFinishDatetime;
}
