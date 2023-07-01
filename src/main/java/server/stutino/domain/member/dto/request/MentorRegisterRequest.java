package server.stutino.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import server.stutino.domain.member.entity.Lesson;
import server.stutino.domain.member.entity.Major;

import javax.validation.constraints.Email;

@Getter
@Setter
@RequiredArgsConstructor
public class MentorRegisterRequest {
    @Email
    private String email;
    private String name;
    private String password;
    private Integer age;
    private String college;
    private Major major;
    private Lesson lesson;
    private Integer grade;
    private String introduce;

    @Builder
    public MentorRegisterRequest(String email, String name, String password, Integer age, String college, Major major, Lesson lesson, Integer grade, String introduce) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.age = age;
        this.college = college;
        this.major = major;
        this.lesson = lesson;
        this.grade = grade;
        this.introduce = introduce;
    }
}
