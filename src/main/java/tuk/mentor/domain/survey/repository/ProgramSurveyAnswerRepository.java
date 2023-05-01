package tuk.mentor.domain.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.survey.entity.ProgramSurveyAnswer;

@Repository
public interface ProgramSurveyAnswerRepository extends JpaRepository<ProgramSurveyAnswer, Long> {
}