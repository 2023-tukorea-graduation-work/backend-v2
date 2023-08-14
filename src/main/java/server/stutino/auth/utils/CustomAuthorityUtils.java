package server.stutino.auth.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthorityUtils {
    private final List<String> ROLE_MENTOR = List.of("ROLE_MENTOR");
    private final List<String> ROLE_MENTEE = List.of("ROLE_MENTEE");
    /*
     * 멘토 role 생성
     */
    public List<String> createMentorRole() {
        return this.ROLE_MENTOR;
    }

    /*
     * 멘티 role 생성
     */
    public List<String> createMenteeRole() {
        return this.ROLE_MENTEE;
    }


    /* 회원 권한 생성*/
    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}