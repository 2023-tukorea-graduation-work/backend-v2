package server.stutino.domain.question.entity;

import lombok.*;
import server.stutino.domain.program.entity.Participants;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participants_id", nullable = false)
    private Participants participants;
    @Column(nullable = false)
    private String questions;
    @Column(nullable = true)
    private String answers;
    @Column(nullable = true)
    private LocalDateTime answerCreatedAt;
    @Column(nullable = true)
    private LocalDateTime answerUpdatedAt;
}
