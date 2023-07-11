package server.stutino.domain.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.stutino.domain.exam.entity.Exam;
import server.stutino.domain.program.entity.Program;
import server.stutino.domain.program.repository.ProgramQueryRepository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
}