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
import tuk.mentor.domain.user.mentor.repository.MentorRepository;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomUserDetailsService implements UserDetailsService {
    CustomAuthorityUtils customAuthorityUtils;
    MentorRepository mentorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = mentorRepository.findByEmail(email);
        
        /*
        * todo: 유저 정보가 없을 경우 예외처리
        *
        * */

        return new CustomUserDetails(customAuthorityUtils, user);
    }
}
