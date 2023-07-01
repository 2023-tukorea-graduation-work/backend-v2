package server.stutino.domain.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProgramWeekDetailResponse {
    private Long id;
    private String content;
    private LocalDate registerDate;
}
