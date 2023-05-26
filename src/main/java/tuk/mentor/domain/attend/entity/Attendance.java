package tuk.mentor.domain.attend.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import tuk.mentor.domain.program.entity.Program;
import tuk.mentor.domain.user.mentee.entity.Mentee;

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
    Integer serial;

    @Builder
    public Attendance(Long id, Mentee mentee, Program program, AttendanceType attendanceType, Integer serial) {
        this.id = id;
        this.mentee = mentee;
        this.program = program;
        this.attendanceType = attendanceType;
        this.serial = serial;
    }
}
