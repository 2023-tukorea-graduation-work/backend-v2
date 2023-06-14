package server.stutino.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.stutino.domain.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    Member findByEmail(String email);
}