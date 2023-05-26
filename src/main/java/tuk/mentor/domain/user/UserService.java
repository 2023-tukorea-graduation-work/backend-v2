package tuk.mentor.domain.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import tuk.mentor.domain.user.mentee.service.MenteeService;
import tuk.mentor.domain.user.mentor.service.MentorService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    MentorService mentorService;
    MenteeService menteeService;
    public Optional<User> getUserByEmail(String email, Role role) {
        switch (role) {
            case MENTOR -> {
                return Optional.ofNullable((User) mentorService.findByEmail(email).
                        orElseThrow(RuntimeException::new));

            }
            case MENTEE -> {
                return Optional.ofNullable((User) menteeService.findByEmail(email).
                        orElseThrow(RuntimeException::new));

            }
            default -> {
                throw new EntityNotFoundException();
            }
        }
    }
}
