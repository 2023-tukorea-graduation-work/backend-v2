package server.stutino.domain.program.dto.request;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProgramRegisterRequest {
    private Long memberId;
    private String subject;
    private String detail;
    private String programStartDate;
    private String programFinishDate;
    private String recruitStartDate;
    private String recruitFinishDate;
    private Integer capacity;
    private String programPlace;
    private List<ProgramWeekRegisterRequest> programWeeks;
    private List<ProgramCategoryRgisterRequest> programCategories;
}
