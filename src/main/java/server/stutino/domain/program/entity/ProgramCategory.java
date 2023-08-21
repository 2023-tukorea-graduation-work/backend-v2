package server.stutino.domain.program.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProgramCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;
    @Column(nullable = false)
    private String parent;
    @Column(nullable = false)
    private String child;

    @Builder
    public ProgramCategory(Program program, String parent, String child) {
        this.program = program;
        this.parent = parent;
        this.child = child;
    }
}
