package server.stutino.domain.schedule.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ScheduleRegisterRequest {
    private Long memberId;
    private String content;
    private String scheduleStartDatetime;
    private String scheduleFinishDatetime;
}
