package tuk.mentor.global.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tuk.mentor.domain.mentee.entity.Mentee;
import tuk.mentor.domain.mentee.repository.MenteeRepository;
import tuk.mentor.domain.mentor.entity.Mentor;
import tuk.mentor.domain.mentor.repository.MentorRepository;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class LoginManager {
    private final String SESSION_NAME = "loginInfo";
    private MentorRepository mentorRepository;
    private MenteeRepository menteeRepository;

    public <T> T getLoginEntity(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        LoginInfo loginInfo = (LoginInfo) session.getAttribute(SESSION_NAME);

        switch (loginInfo.getRole()) {
            case MENTOR -> {
                Mentor mentor = mentorRepository.findById(loginInfo.getUserID()).
                        orElseThrow(RuntimeException::new);
                return (T) mentor;
            }
            case MENTEE -> {
                Mentee mentee = menteeRepository.findById(loginInfo.getUserID()).
                        orElseThrow(RuntimeException::new);
                return (T) mentee;
            }
            default -> {
                throw new EntityNotFoundException();
            }
        }
    }
}
