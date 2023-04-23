package tuk.mentor.domain.program.entity;

import lombok.*;
import tuk.mentor.domain.program.entity.Program;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;
    private String content;
    private LocalDate programWeekStartDate;
    private LocalDate programWeekFinishDate;
}
