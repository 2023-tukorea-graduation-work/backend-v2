package server.stutino.auth.userdetails;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import server.stutino.auth.utils.CustomAuthorityUtils;
import server.stutino.domain.member.entity.Member;

import java.util.Collection;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomUserDetails implements UserDetails {
    CustomAuthorityUtils authorityUtils;
    Long memberId;
    String email;
    String password;
    List<String> roles;

    public CustomUserDetails(CustomAuthorityUtils authorityUtils, Member member) {
        this.authorityUtils = authorityUtils;
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.roles = member.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityUtils.createAuthorities(roles);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
