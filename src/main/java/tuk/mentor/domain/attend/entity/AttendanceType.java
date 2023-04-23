package tuk.mentor.domain.attend.entity;

public enum AttendanceType {
    ATTENDANCE("출석"),
    TARDY("지각"),
    ABSENT("결석");

    private final String attendanceType;

    AttendanceType(String attendanceType) {
        this.attendanceType = attendanceType;
    }
}
