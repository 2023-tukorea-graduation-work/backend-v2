package server.stutino.domain.exam.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MultipleChoiceOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "multiple_choice_question_id")
    private MultipleChoiceQuestion multipleChoiceQuestion;
    @Column(nullable = false)
    private String choices;
}
