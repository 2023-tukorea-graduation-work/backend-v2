package server.stutino.domain.program.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProgramWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 프로그램:프로그램 주차 = 1:N
     * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;
    private String content;
    private LocalDate registerDate;


    public ProgramWeek(Program program, String content, LocalDate registerDate) {
        this.program = program;
        this.content = content;
        this.registerDate = registerDate;
    }
}
