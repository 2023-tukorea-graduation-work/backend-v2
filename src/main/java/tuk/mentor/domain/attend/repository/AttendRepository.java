package tuk.mentor.domain.attend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.attend.entity.Attendance;

@Repository
public interface AttendRepository extends JpaRepository<Attendance, Long> {
}