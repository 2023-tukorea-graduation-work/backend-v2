package server.stutino.domain.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.stutino.domain.question.repository.QuestionQueryRepository;
import server.stutino.domain.task.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, QuestionQueryRepository {
}