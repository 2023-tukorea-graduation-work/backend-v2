package tuk.mentor.domain.user.mentee.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@RequiredArgsConstructor
public class MenteeRegisterRequest {
    @Email
    private String email;
    private String name;
    private String password;
    private Integer age;
    private String school;
    private Integer grade;
    private String introduce;
}
