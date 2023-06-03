package tuk.mentor.auth.userdetails;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tuk.mentor.auth.utils.CustomAuthorityUtils;
import tuk.mentor.domain.user.User;
import tuk.mentor.domain.user.mentee.entity.Mentee;
import tuk.mentor.domain.user.mentee.repository.MenteeRepository;
import tuk.mentor.domain.user.mentor.entity.Mentor;
import tuk.mentor.domain.user.mentor.repository.MentorRepository;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomUserDetailsService implements UserDetailsService {
    CustomAuthorityUtils customAuthorityUtils;
    MentorRepository mentorRepository;
    MenteeRepository menteeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;

        // [1] mentor로 로그인 했을 경우
        Mentor mentor = mentorRepository.findByEmail(email);
        if(mentor != null) {
            user = User.builder()
                    .userId(mentor.getId())
                    .email(mentor.getEmail())
                    .password(mentor.getPassword())
                    .roles(mentor.getRoles())
                    .build();
        }
        else {
            // [2] mentee로 로그인 했을 경우
            Mentee mentee = menteeRepository.findByEmail(email);

            if(mentee != null) {
                user = User.builder()
                        .userId(mentee.getId())
                        .email(mentee.getEmail())
                        .password(mentee.getPassword())
                        .roles(mentee.getRoles())
                        .build();
            }
            
            // [3] admin로 로그인 했을 경우
        }

        /*
        * todo: 유저 정보가 없을 경우 예외처리
        *
        * */

        return new CustomUserDetails(customAuthorityUtils, user);
    }
}
