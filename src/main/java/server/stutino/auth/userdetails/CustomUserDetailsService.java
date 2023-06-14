package server.stutino.auth.userdetails;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import server.stutino.auth.utils.CustomAuthorityUtils;
import server.stutino.domain.member.entity.Member;
import server.stutino.domain.member.repository.MemberRepository;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomUserDetailsService implements UserDetailsService {
    CustomAuthorityUtils customAuthorityUtils;
    MemberRepository memberReposiotry;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // [1] mentor로 로그인 했을 경우
        Member member = memberReposiotry.findByEmail(email);
        return new CustomUserDetails(customAuthorityUtils, member);
    }
}
