package server.stutino.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class MenteeRegisterRequest {
    @Email
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private Integer age;
    @NotNull
    private String school;
    @NotNull
    private Integer grade;
    @NotNull
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
