package tuk.mentor.domain.matirial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.matirial.entity.ProgramMaterial;
import tuk.mentor.domain.program.entity.Program;
import tuk.mentor.domain.program.repository.ProgramQueryRepository;

@Repository
public interface MaterialRepository extends JpaRepository<ProgramMaterial, Long>, ProgramQueryRepository {
}