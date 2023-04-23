package tuk.mentor.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.schedule.entity.MenteeSchedule;

import java.util.List;

@Repository
public interface MenteeScheduleRepository extends JpaRepository<MenteeSchedule, Long> {
    @Query("SELECT ms FROM MenteeSchedule ms WHERE ms.mentee.id = :menteeId")
    List<MenteeSchedule> getScheduleList(@Param("menteeId") Long menteeId);
}