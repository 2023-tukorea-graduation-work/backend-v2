package server.stutino.domain.exam.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
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
    @Column(nullable = false)
    private boolean isCorrect;

    @Builder
    public MultipleChoiceOptions(MultipleChoiceQuestion multipleChoiceQuestion, String choices, boolean isCorrect) {
        this.multipleChoiceQuestion = multipleChoiceQuestion;
        this.choices = choices;
        this.isCorrect = isCorrect;
    }
}
