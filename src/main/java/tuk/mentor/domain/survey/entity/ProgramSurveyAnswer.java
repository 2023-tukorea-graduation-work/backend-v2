package tuk.mentor.domain.survey.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tuk.mentor.domain.program.entity.Program;
import tuk.mentor.domain.user.mentee.entity.Mentee;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramSurveyAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;
    private Long surveyId;
    private Long answerId;
}
