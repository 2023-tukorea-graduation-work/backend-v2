package tuk.mentor.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.program.entity.ProgramParticipation;

@Repository
public interface ProgramParticipationRepository extends JpaRepository<ProgramParticipation, Long> {
}