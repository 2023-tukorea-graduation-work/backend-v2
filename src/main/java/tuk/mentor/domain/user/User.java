package tuk.mentor.domain.user;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class User {
    private Long userId;
    private String email;
    private String password;
    private List<String> roles = new ArrayList<>();
}
