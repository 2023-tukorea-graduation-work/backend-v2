package tuk.mentor.global.login;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import tuk.mentor.domain.mentee.entity.Mentee;
import tuk.mentor.domain.mentee.service.MenteeService;
import tuk.mentor.domain.mentor.entity.Mentor;
import tuk.mentor.domain.mentor.service.MentorService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MentorService mentorService;
    private final MenteeService menteeService;

    public LoginResponse login(LoginRequest request) {
        switch (request.getRole()) {
            case MENTOR -> {
                Optional<Mentor> mentor = mentorService.findByEmail(request.getEmail());
                if (mentor.isPresent() && BCrypt.checkpw(request.getPassword(), mentor.get().getPassword())) {
                    return LoginResponse.builder()
                            .id(mentor.get().getId())
                            .role(request.getRole())
                            .build();
                }
            }
            case MENTEE -> {
                Optional<Mentee> mentee = menteeService.findByEmail(request.getEmail());
                if (mentee.isPresent() && BCrypt.checkpw(request.getPassword(), mentee.get().getPassword())) {
                    return LoginResponse.builder()
                            .id(mentee.get().getId())
                            .role(request.getRole())
                            .build();
                }
            }
        }
        return null;
    }
}
