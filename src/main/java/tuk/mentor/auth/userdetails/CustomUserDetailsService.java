package tuk.mentor.auth.userdetails;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tuk.mentor.auth.utils.CustomAuthorityUtils;
import tuk.mentor.domain.user.Role;
import tuk.mentor.domain.user.User;
import tuk.mentor.domain.user.UserService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomUserDetailsService implements UserDetailsService {
    CustomAuthorityUtils customAuthorityUtils;
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByEmail(email, Role.MENTOR);

        /*
        * todo: 유저 정보가 없을 경우 예외처
        *
        * */

        return new CustomUserDetails(customAuthorityUtils, user);
    }


}
