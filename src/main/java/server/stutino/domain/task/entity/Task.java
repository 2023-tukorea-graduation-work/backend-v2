package server.stutino.domain.task.entity;

import lombok.*;
import server.stutino.domain.program.entity.Program;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
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
    private LocalDateTime startTaskDateTime;
    @Column(nullable = false)
    private LocalDateTime endTaskDateTime;
}
