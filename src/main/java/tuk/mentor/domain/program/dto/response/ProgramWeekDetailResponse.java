package tuk.mentor.domain.program.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class ProgramWeekDetailResponse {
    private Long id;
    private String content;
    private LocalDate programWeekStartDate;
    private LocalDate programWeekFinishDate;
}
