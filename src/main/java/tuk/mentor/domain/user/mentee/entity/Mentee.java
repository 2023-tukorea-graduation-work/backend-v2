package tuk.mentor.domain.user.mentee.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.URL;
import tuk.mentor.domain.user.Role;
import tuk.mentor.domain.user.User;

import javax.persistence.*;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Mentee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    @Enumerated(EnumType.STRING)
    private final Role role = Role.MENTEE;
    private String name;
    private Integer age;
    private String school;
    private Integer grade;
    private String introduce;
    @URL
    private String imgUrl;
}
