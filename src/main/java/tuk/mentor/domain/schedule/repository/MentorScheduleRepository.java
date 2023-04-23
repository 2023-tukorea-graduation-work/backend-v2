package tuk.mentor.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.schedule.entity.MentorSchedule;

import java.util.List;

@Repository
public interface MentorScheduleRepository extends JpaRepository<MentorSchedule, Long> {
    @Query("SELECT ms FROM MentorSchedule ms WHERE ms.mentor.id = :mentorId")
    List<MentorSchedule> getScheduleList(@Param("mentorId") Long mentorId);
}