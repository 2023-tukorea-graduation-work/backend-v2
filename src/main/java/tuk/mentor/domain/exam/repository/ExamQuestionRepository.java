package tuk.mentor.domain.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.exam.entity.ExamQuestion;

import java.util.List;

@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {
    @Query("SELECT eq FROM ExamQuestion eq WHERE eq.exam.id = :examId")
    List<ExamQuestion> findExamQuestionByExamId(@Param("examId") Long examId);
}