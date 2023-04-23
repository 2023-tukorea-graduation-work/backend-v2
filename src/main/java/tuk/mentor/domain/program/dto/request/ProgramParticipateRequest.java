package tuk.mentor.domain.program.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProgramParticipateRequest {
    private Long menteeId;
    private Long programId;
}
