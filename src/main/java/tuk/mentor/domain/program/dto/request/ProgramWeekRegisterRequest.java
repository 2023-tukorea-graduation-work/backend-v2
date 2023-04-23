package tuk.mentor.domain.program.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProgramWeekRegisterRequest {
    private String content;
    private String programWeekStartDate;
    private String programWeekFinishDate;
}
