package server.stutino.domain.exam.entity;

import lombok.*;
import server.stutino.domain.program.entity.Program;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
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
    private LocalDateTime examStartTime;
    @Column(nullable = false)
    private LocalDateTime examFinishTime;
    @Column(nullable = false)
    private Boolean isExamRegistered;
}
