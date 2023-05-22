package tuk.mentor.domain.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.exam.entity.ExamItem;

import java.util.List;

@Repository
public interface ExamItemRepository extends JpaRepository<ExamItem, Long> {
    @Query("SELECT ei FROM ExamItem ei WHERE ei.examQuestion.id = :examQuestionId")
    List<ExamItem> findExamItemByExamQuestionId(@Param("examQuestionId") Long examQuestionId);
}