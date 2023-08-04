package server.stutino.domain.matirial.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class MaterialRegisterRequest {
    @NotNull
    private Long programId;
    @NotBlank
    private String title;
    @NotBlank
    private String detail;

    @Builder
    public MaterialRegisterRequest(Long programId, String title, String detail) {
        this.programId = programId;
        this.title = title;
        this.detail = detail;
    }
}
