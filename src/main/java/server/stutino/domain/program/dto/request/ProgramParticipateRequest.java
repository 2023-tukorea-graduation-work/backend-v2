package server.stutino.domain.program.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProgramParticipateRequest {
    @NotNull
    private Long menteeId;
    @NotNull
    private Long programId;
}
