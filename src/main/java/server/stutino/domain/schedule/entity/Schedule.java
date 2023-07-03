package server.stutino.domain.schedule.entity;

import lombok.*;
import server.stutino.domain.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;
    @Column(nullable = false)
    private String content;
    @Column(columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime scheduleStartDatetime;
    @Column(columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime scheduleFinishDatetime;
}
