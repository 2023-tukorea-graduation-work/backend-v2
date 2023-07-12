package server.stutino.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.stutino.domain.member.entity.Member;
import server.stutino.domain.program.entity.Participants;

import java.util.List;

@Repository
public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
    @Query("SELECT pp.member FROM Participants pp WHERE pp.program.id = :programId")
    List<Member> findParticipantByProgramId(@Param("programId") Long programId);

    @Query("SELECT count(pp.id) FROM Participants pp WHERE pp.program.id = :programId and pp.member.id = :memberId")
    Long isParticipated(@Param("programId") Long programId, @Param("memberId") Long memberId);
}