package server.stutino.domain.notice.entity;


import lombok.*;
import server.stutino.domain.program.entity.Program;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
}
