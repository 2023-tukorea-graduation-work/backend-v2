package server.stutino.domain.matirial.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaterialDetailResponse {
    private Long materialId;
    private String title;
    private String detail;
    private String fileName;
}
