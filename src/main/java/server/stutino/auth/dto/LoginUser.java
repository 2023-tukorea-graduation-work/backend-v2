package server.stutino.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import server.stutino.domain.member.entity.Member;

import java.util.Collection;
import java.util.Map;

/**
 * login 유저정보에 대한 dto
 *
 * @author Hyeonjun Park
 */
@Getter
@AllArgsConstructor
public class LoginUser implements UserDetails {
    private Member member;
    private Map<String, Object> attribute;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}