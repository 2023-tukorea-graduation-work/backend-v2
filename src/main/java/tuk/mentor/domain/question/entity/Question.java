package tuk.mentor.domain.question.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tuk.mentor.domain.user.mentee.entity.Mentee;
import tuk.mentor.domain.program.entity.Program;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id", nullable = false)
    private Mentee mentee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;
    @Column(nullable = false)
    private String question;
    @Column(nullable = true)
    private String answer;
    @Column(nullable = true)
    private LocalDateTime answerCreatedAt;
    @Column(nullable = true)
    private LocalDateTime answerUpdatedAt;
}
