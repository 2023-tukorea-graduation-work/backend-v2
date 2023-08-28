package server.stutino.domain.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProgramCategoryResponse {
    private String parent;
    private String child;
}
