package tuk.mentor.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.program.entity.ProgramWeek;

import java.util.List;

@Repository
public interface ProgramWeekRepository extends JpaRepository<ProgramWeek, Long> {
    @Query("SELECT pw FROM ProgramWeek pw WHERE pw.program.id = :programId")
    List<ProgramWeek> getProgramWeekByProgramId(@Param("programId") Long programId);
}