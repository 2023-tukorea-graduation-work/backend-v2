package server.stutino.domain.exam.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MultipleChoiceQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;
    @Column(nullable = false)
    private Integer score;
    @Column(nullable = false)
    private String question;
    @OneToMany(mappedBy = "multipleChoiceQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultipleChoiceOptions> multipleChoiceOptions;

    @Builder
    public MultipleChoiceQuestion(Exam exam, Integer score, String question) {
        this.exam = exam;
        this.score = score;
        this.question = question;
    }
}
