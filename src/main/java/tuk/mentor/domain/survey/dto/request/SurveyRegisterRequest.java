package tuk.mentor.domain.survey.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class SurveyRegisterRequest {
    private Long programId;
    private List<Long> answers;
    private String comment;
    private String review;
}
