package tuk.mentor.domain.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_id")
    private Exam exam;
    private ExamQuestionType examQuestionType;
    private String question;
    private String correctAnswer;
    private Integer score;
    @OneToMany(mappedBy = "examQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamItem> examItems;
}
