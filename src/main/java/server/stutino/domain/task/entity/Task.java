package server.stutino.domain.task.entity;

import lombok.*;
import server.stutino.domain.program.entity.Program;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;
    @Column(nullable = false)
    private String title;;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private LocalDate startTaskDate;
    @Column(nullable = false)
    private LocalDate endTaskDate;

    @Builder
    public Task(Program program, String title, String content, LocalDate startTaskDate, LocalDate endTaskDate) {
        this.program = program;
        this.title = title;
        this.content = content;
        this.startTaskDate = startTaskDate;
        this.endTaskDate = endTaskDate;
    }
}
