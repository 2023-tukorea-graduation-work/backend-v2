package server.stutino.domain.schedule.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ScheduleRegisterRequest {
    @NotNull
    private Long memberId;
    @NotNull
    private String content;
    @NotNull
    private String scheduleStartDatetime;
    @NotNull
    private String scheduleFinishDatetime;
}
