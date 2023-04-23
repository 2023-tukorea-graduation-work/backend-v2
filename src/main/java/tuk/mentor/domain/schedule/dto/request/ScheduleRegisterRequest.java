package tuk.mentor.domain.schedule.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ScheduleRegisterRequest {
    private String content;
    private String scheduleStartDatetime;
    private String scheduleFinishDatetime;
}
