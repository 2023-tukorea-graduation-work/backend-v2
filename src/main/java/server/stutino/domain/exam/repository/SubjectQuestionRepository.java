package server.stutino.domain.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.stutino.domain.exam.entity.SubjectQuestion;

@org.springframework.stereotype.Repository
public interface SubjectQuestionRepository extends JpaRepository<SubjectQuestion, Long> {
}