package server.stutino.domain.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.stutino.domain.exam.entity.MultipleChoiceOptions;

import java.util.List;

@Repository
public interface MultipleChoiceOptionsRepository extends JpaRepository<MultipleChoiceOptions, Long> {
    @Query("select mco from MultipleChoiceOptions mco where mco.multipleChoiceQuestion.id = :mcqId")
    List<MultipleChoiceOptions> findMultipleChoiceOptionsByQuestionId(@Param("mcqId")Long mcqId);
}