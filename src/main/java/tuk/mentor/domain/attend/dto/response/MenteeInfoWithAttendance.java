package tuk.mentor.domain.attend.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenteeInfoWithAttendance {
    String profileImgUrl;
    String menteeName;
    String school;
    Integer age;
    Integer attendanceCnt;
    Integer tardyCnt;
    Integer absentCnt;
}
