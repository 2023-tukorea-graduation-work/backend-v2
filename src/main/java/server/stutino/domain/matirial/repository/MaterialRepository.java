package server.stutino.domain.matirial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.stutino.domain.matirial.entity.ProgramMaterial;
import server.stutino.domain.program.repository.ProgramQueryRepository;

@Repository
public interface MaterialRepository extends JpaRepository<ProgramMaterial, Long>, ProgramQueryRepository {
}