package server.stutino.domain.member.dto.request;

import lombok.Builder;
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

    @Builder
    public MenteeRegisterRequest(String email, String name, String password, Integer age, String school, Integer grade, String introduce) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.age = age;
        this.school = school;
        this.grade = grade;
        this.introduce = introduce;
    }
}
