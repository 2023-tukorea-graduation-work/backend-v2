package server.stutino.domain.exam.entity;

import lombok.*;
import server.stutino.domain.program.entity.Program;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private LocalDate examStartDate;
    @Column(nullable = false)
    private LocalDate examFinishDate;
    @Column(nullable = false)
    private Boolean isExamRegistered;

    @Builder
    public Exam(Program program, String title, LocalDate examStartDate, LocalDate examFinishDate, Boolean isExamRegistered) {
        this.program = program;
        this.title = title;
        this.examStartDate = examStartDate;
        this.examFinishDate = examFinishDate;
        this.isExamRegistered = isExamRegistered;
    }
}
