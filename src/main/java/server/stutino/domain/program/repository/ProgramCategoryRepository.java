package server.stutino.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.stutino.domain.program.entity.ProgramCategory;

@Repository
public interface ProgramCategoryRepository extends JpaRepository<ProgramCategory, Long>{
}