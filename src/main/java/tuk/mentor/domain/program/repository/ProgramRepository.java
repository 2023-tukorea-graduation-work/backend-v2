package tuk.mentor.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.program.entity.Program;
import tuk.mentor.domain.question.entity.Question;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long>, ProgramQueryRepository{
}