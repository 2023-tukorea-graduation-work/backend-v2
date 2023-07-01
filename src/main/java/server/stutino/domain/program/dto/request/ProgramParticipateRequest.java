package server.stutino.domain.program.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProgramParticipateRequest {
    private Long menteeId;
    private Long programId;
}
