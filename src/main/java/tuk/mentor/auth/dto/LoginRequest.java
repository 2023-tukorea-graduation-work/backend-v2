package tuk.mentor.auth.dto;

import lombok.Getter;
import tuk.mentor.domain.user.Role;

@Getter
public class LoginRequest {
    private String email;
    private String password;
    private Role role;
}
