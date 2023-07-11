package server.stutino.domain.program.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProgramRegisterRequest {
    @NotNull
    private Long memberId;
    @NotNull
    private String subject;
    @NotNull
    private String detail;
    @NotNull
    private String programStartDate;
    @NotNull
    private String programFinishDate;
    @NotNull
    private String recruitStartDate;
    @NotNull
    private String recruitFinishDate;
    @NotNull
    private Integer capacity;
    @NotNull
    private String programPlace;
    @NotNull
    private List<ProgramWeekRegisterRequest> programWeeks;
    @NotNull
    private List<ProgramCategoryRgisterRequest> programCategories;
}
