package tuk.mentor.domain.attend.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import tuk.mentor.domain.mentee.entity.Mentee;
import tuk.mentor.domain.program.entity.Program;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id", nullable = false)
    Mentee mentee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    Program program;
    @Enumerated(EnumType.STRING)
    AttendanceType attendanceType;
    Integer order;

    @Builder
    public Attendance(Long id, Mentee mentee, Program program, AttendanceType attendanceType, Integer order) {
        this.id = id;
        this.mentee = mentee;
        this.program = program;
        this.attendanceType = attendanceType;
        this.order = order;
    }
}
