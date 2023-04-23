package tuk.mentor.domain.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tuk.mentor.domain.mentor.entity.Mentor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id", nullable = true)
    private Mentor mentor;
    private String content;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime scheduleStartDatetime;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime scheduleFinishDatetime;
}
