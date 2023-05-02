package tuk.mentor.domain.survey.dto.response;

import lombok.*;
import tuk.mentor.domain.survey.entity.Survey;
import tuk.mentor.domain.survey.entity.SurveyAnswer;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDefaultResponse {
    private List<Survey> questions;
    private List<SurveyAnswer> answers;
}
