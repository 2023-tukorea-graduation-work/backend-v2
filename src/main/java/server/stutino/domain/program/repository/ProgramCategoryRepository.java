package server.stutino.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.stutino.domain.program.entity.ProgramCategory;

import java.util.List;

@Repository
public interface ProgramCategoryRepository extends JpaRepository<ProgramCategory, Long>{
    @Query("SELECT pc FROM ProgramCategory pc WHERE pc.program.id = :programId")
    List<ProgramCategory> getProgramCategoriesByProgramId(@Param("programId") Long programId);
}