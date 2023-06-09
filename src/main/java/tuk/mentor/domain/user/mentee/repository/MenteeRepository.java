package tuk.mentor.domain.user.mentee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.user.mentee.entity.Mentee;

@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Long> ,MenteeQueryRepository{
    /*@Query("SELECT m from Mentee m where m.email = :email")
    Mentee findByEmail(@Param("email") String email);*/
    Mentee findByEmail(String email);
}