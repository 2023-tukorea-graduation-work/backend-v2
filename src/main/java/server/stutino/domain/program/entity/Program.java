package server.stutino.domain.program.entity;

import lombok.*;
import server.stutino.domain.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    * 회원:프로그램 = 1:N
    * -> 프로그램 생성(멘토 기준)
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
//    @OneToMany(mappedBy = "program", cascade = {CascadeType.ALL}, orphanRemoval = true)
//    private List<ProgramWeek> programWeeks;
//    @OneToMany(mappedBy = "program", cascade = {CascadeType.ALL}, orphanRemoval = true)
//    private List<ProgramCategory> programCategories;
//    @OneToMany(mappedBy = "program", cascade = {CascadeType.ALL}, orphanRemoval = true)
//    private List<Notice> notices;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String detail;
    @Column(nullable = false)
    private LocalDate programStartDate;
    @Column(nullable = false)
    private LocalDate programFinishDate;
    @Column(nullable = false)
    private LocalDate recruitStartDate;
    @Column(nullable = false)
    private LocalDate recruitFinishDate;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    private String programPlace;
    @Enumerated(EnumType.STRING)
    private ProgramState programState;
}
