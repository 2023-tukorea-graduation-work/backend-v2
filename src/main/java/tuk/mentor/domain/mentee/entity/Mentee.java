package tuk.mentor.domain.mentee.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import tuk.mentor.domain.mentor.entity.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mentee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    @Enumerated(EnumType.STRING)
    private final Role role = Role.MENTEE;
    private String email;
    private String name;
    private String password;
    private Integer age;
    private String school;
    private Integer grade;
    private String introduce;
    @URL
    private String imgUrl;
}
