package tuk.mentor.domain.program.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ProgramRegisterRequest {
    private String subject;
    private String detail;
    private String programStartDate;
    private String programFinishDate;
    private String recruitStartDate;
    private String recruitFinishDate;
    private Integer capacity;
    private String programPlace;
    private List<ProgramWeekRegisterRequest> programWeeks;
}
