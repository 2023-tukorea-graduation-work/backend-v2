package server.stutino.domain.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassedMentorRequest {
    @NotNull
    private Long mentorId;
    @NotNull
    private Boolean isPassed;
}
