package server.stutino.domain.exam.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExamRegisterRequest {
    @NotNull
    private Long programId;
    @NotBlank
    private String examTitle;
    @NotNull
    private String examStartTime;
    @NotNull
    private String examFinishTime;
    @NotNull
    private Boolean isExamRegistered;
    private List<ExamQuestionRegisterRequest> examQuestionRegisterRequest;
}
