package tuk.mentor.domain.mentee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.mentee.entity.Mentee;

import java.util.Optional;

@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Long> ,MenteeQueryRepository{
    @Query("SELECT m from Mentee m where m.email = :email")
    Optional<Mentee> findByEmail(@Param("email") String email);
}