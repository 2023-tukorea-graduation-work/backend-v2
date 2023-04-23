package tuk.mentor.domain.mentor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.mentor.entity.Mentor;

import java.util.Optional;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> ,MentorQueryRepository{
    @Query("SELECT m from Mentor m where m.email = :email")
    Optional<Mentor> findByEmail(@Param("email") String email);
}