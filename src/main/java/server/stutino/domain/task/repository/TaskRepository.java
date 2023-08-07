package server.stutino.domain.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.stutino.domain.question.repository.QuestionQueryRepository;
import server.stutino.domain.task.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, QuestionQueryRepository {
    @Query("select t from Task t where t.program.id = :programId")
    List<Task> findAllTaskByProgram(@Param("programId") Long programId);
}