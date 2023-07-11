package server.stutino.domain.exam.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExamRegisterRequest {
    @NotNull
    private Long programId;
    @NotNull
    private String examTitle;
    @NotNull
    private String examStartTime;
    @NotNull
    private String examFinishTime;
    @NotNull
    private String isExamRegistered;
    @NotNull
    private ExamQuestionRegisterRequest examQuestionRegisterRequest;
}
