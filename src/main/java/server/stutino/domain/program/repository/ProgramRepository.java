package server.stutino.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.stutino.domain.program.entity.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long>, ProgramQueryRepository{
}