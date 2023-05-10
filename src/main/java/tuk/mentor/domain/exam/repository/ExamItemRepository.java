package tuk.mentor.domain.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.exam.entity.ExamItem;

@Repository
public interface ExamItemRepository extends JpaRepository<ExamItem, Long> {
}