package server.stutino.domain.task.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TaskRegisterRequest {
    @NotNull
    private Long programId;
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String startTaskDateTime;
    @NotNull
    private String endTaskDateTime;
}
