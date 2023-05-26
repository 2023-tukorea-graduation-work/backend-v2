package tuk.mentor.domain.user.mentor.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tuk.mentor.domain.user.mentor.entity.Lesson;
import tuk.mentor.domain.user.mentor.entity.Major;

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
}
