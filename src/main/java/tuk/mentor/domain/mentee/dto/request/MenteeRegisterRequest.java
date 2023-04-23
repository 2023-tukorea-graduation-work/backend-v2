package tuk.mentor.domain.mentee.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MenteeRegisterRequest {
    private String name;
    private String email;
    private String password;
    private Integer age;
    private String school;
    private Integer grade;
    private String introduce;
}
