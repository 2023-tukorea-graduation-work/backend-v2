package tuk.mentor.domain.user.mentor.entity;


import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;
import tuk.mentor.domain.user.Role;
import tuk.mentor.domain.user.User;

import javax.persistence.*;

@Getter
@Entity
@Builder
public class Mentor extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String college;
    @Enumerated(EnumType.STRING)
    private Major major;
    @Enumerated(EnumType.STRING)
    private Lesson lesson;
    private Integer grade;
    private String introduce;
    @URL
    private String imgUrl;

    public Mentor() {
        super(Role.MENTOR);
    }
    @Override
    public Role getRole() {
        return super.getRole();
    }
}
