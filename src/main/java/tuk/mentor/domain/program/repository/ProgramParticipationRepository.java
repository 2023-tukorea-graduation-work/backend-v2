package tuk.mentor.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.program.entity.ProgramParticipation;
import tuk.mentor.domain.program.entity.ProgramWeek;
import tuk.mentor.domain.user.mentee.entity.Mentee;

import java.util.List;

@Repository
public interface ProgramParticipationRepository extends JpaRepository<ProgramParticipation, Long> {
    @Query("SELECT pp.mentee FROM ProgramParticipation pp WHERE pp.program.id = :programId")
    List<Mentee> findParticipantByProgramId(@Param("programId") Long programId);
}