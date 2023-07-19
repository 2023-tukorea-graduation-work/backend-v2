package server.stutino.domain.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.stutino.domain.exam.entity.MultipleChoiceQuestion;

import java.util.List;

@Repository
public interface MultipleChoiceQuestionRepository extends JpaRepository<MultipleChoiceQuestion, Long> {
    @Query("select mcq from MultipleChoiceQuestion mcq where mcq.exam.id = :examId")
    List<MultipleChoiceQuestion> findAllMultiQuestionByExamId(@Param("examId") Long examId);
}