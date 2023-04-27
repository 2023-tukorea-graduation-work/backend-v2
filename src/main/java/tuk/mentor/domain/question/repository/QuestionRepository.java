package tuk.mentor.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.program.repository.ProgramQueryRepository;
import tuk.mentor.domain.question.entity.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, ProgramQueryRepository {
    @Query("SELECT q FROM Question q WHERE q.program.id = :programId")
    List<Question> getAllQuestionByProgramId(@Param("programId") Long programId);
}