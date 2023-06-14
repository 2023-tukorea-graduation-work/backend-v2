package server.stutino.domain.member.entity;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private String institution;
    @Column(nullable = false)
    private Integer grade;

    /*
    * major, lesson 은 mentor 만 갖는 필드
    * */
    @Enumerated(EnumType.STRING)
    private Major major;
    @Enumerated(EnumType.STRING)
    private Lesson lesson;
    @Column(nullable = false)
    private String introduce;
    @URL
    @Column(nullable = false)
    private String imgUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
}