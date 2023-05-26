package tuk.mentor.auth.userdetails;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tuk.mentor.auth.utils.CustomAuthorityUtils;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 권한 가져오기
        String authority = authentication.getAuthorities().iterator().next().getAuthority();
        Optional<User> user = userService.getUserByEmail(email, authority);

        /*
        * todo: 유저 정보가 없을 경우 예외처리
        *
        * */

        return new CustomUserDetails(customAuthorityUtils, user.get());
    }
}
