package server.stutino.domain.matirial.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MaterialRegisterRequest {
    @NotNull
    private Long programId;
    @NotBlank
    private String title;
    @NotBlank
    private String detail;
}
