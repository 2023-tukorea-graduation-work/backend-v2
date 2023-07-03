package server.stutino.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.stutino.domain.notice.entity.Notice;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query("SELECT n from Notice n where n.program.id = :programId")
    List<Notice> findAllByProgramId(@Param("programId") Long programId);
}