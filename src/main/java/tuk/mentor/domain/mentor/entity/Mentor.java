package tuk.mentor.domain.mentor.entity;


import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    @Enumerated(EnumType.STRING)
    private final Role role = Role.MENTOR;
    private String email;
    private String name;
    private String password;
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
}
