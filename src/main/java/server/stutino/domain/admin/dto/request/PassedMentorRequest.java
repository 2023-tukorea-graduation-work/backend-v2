package server.stutino.domain.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class PassedMentorRequest {
    @NotNull
    private Long mentorId;
    @NotNull
    private Boolean isPassed;
}
