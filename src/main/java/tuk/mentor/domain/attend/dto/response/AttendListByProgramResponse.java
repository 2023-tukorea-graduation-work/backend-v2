package tuk.mentor.domain.attend.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendListByProgramResponse {
    List<MenteeInfoWithAttendance> attendances;
    LocalDate programWeekDate;
    Integer currentSerial;
    Integer finalSerial;
    LocalDate programStartDate;
    LocalDate programFinishDate;
}
