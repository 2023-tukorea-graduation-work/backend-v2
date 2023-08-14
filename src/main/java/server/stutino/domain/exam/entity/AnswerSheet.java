package server.stutino.domain.exam.entity;

import lombok.*;
import server.stutino.domain.member.entity.Member;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AnswerSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;
    @Enumerated(EnumType.STRING)
    private QuestionType QuestionType;
    @Column(nullable = false)
    private Long questionId;
    @Column(nullable = true)
    private Long multipleChoiceAnswer;
    @Column(nullable = true)
    private String subjectAnswer;
}
