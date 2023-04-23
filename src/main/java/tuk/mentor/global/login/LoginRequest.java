package tuk.mentor.global.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tuk.mentor.domain.mentor.entity.Role;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
    private Role role;
}
