package server.stutino.domain.matirial.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaterialDownloadResponse {
    private String filename;
    private String filepath;
    private byte [] data;
}
