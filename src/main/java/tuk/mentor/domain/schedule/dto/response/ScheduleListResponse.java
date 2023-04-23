package tuk.mentor.domain.schedule.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ScheduleListResponse {
    private String content;
    private String scheduleStartDatetime;
    private String scheduleFinishDatetime;
}
