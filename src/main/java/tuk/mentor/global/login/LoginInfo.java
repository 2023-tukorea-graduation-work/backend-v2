package tuk.mentor.global.login;

import lombok.*;
import org.springframework.stereotype.Component;
import tuk.mentor.domain.mentor.entity.Role;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
    private Long userID;
    private Role role;
}
