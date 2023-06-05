package tuk.mentor.domain.attend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.attend.entity.Attendance;
import tuk.mentor.domain.user.mentee.entity.Mentee;

import java.util.List;

@Repository
public interface AttendRepository extends JpaRepository<Attendance, Long> {
    @Query("SELECT count() FROM ProgramParticipation pp WHERE pp.program.id = :programId")
    Long findParticipantByProgramId(@Param("programId") Long programId);
}