package server.stutino.domain.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.stutino.domain.exam.entity.SubjectQuestion;

import java.util.List;

@org.springframework.stereotype.Repository
public interface SubjectQuestionRepository extends JpaRepository<SubjectQuestion, Long> {
    @Query("select sq from SubjectQuestion sq where sq.exam.id = :examId")
    List<SubjectQuestion> findSubjectQuestionByExamId(@Param("examId") Long examId);
}