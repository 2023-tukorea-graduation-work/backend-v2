package server.stutino.domain.program.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProgramCategoryRgisterRequest {
    @NotNull
    private String parent;
    @NotNull
    private String child;
}
