package tuk.mentor.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.notice.entity.ProgramNotice;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<ProgramNotice, Long> {
    @Query("SELECT pn from ProgramNotice pn where pn.program.id = :programId")
    List<ProgramNotice> findAllByProgramId(@Param("programId") Long programId);
}