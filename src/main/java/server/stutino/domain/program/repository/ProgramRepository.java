package server.stutino.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.stutino.domain.program.entity.Program;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long>, ProgramQueryRepository{
    @Query("select p from Program p where p.member.id = :memberId")
    List<Program> findProgramByMemberId(@Param("memberId") Long memberId);
}