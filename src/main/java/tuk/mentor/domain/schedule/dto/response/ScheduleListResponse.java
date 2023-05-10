package tuk.mentor.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ScheduleListResponse {
    private String content;
    private String scheduleStartDatetime;
    private String scheduleFinishDatetime;
}
