package server.stutino.support.fixture.member;

import lombok.Getter;
import server.stutino.domain.member.entity.Lesson;
import server.stutino.domain.member.entity.Major;
import server.stutino.domain.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum MemberFixture {
    MINCHANG(
            "wjdalsckd@naver.com",
            "0000",
            "정민창",
            24,
            "한국공학대학교",
            4,
            Major.IT_MANAGEMENT,
            Lesson.OFFLINE,
            "잘부탁드립니다.",
            "",
            new ArrayList<>());
    private String email;
    private String password;
    private String name;
    private Integer age;
    private String institution;
    private Integer grade;
    private Major major;
    private Lesson lesson;
    private String introduce;
    private String imgUrl;
    private List<String> roles;

    MemberFixture(String email, String password, String name, Integer age, String institution, Integer grade, Major major, Lesson lesson, String introduce, String imgUrl, List<String> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.institution = institution;
        this.grade = grade;
        this.major = major;
        this.lesson = lesson;
        this.introduce = introduce;
        this.imgUrl = imgUrl;
        this.roles = roles;
    }

    public Member toEntity() {
        return Member.builder().email(email).name(name).build();
    }
}
