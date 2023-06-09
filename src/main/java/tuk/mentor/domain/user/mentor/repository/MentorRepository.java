package tuk.mentor.domain.user.mentor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.user.mentor.entity.Mentor;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> ,MentorQueryRepository{
    /*@Query("SELECT m from Mentor m where m.email = :email")
    Mentor findByEmail(@Param("email") String email);*/

    Mentor findByEmail(String email);
}