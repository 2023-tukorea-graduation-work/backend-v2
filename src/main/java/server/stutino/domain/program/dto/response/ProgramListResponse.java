package server.stutino.domain.program.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import server.stutino.domain.member.entity.Lesson;
import server.stutino.domain.member.entity.Major;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProgramListResponse {
    private String memberName;
    private String institution;
    private Major major;
    private Lesson lesson;
    private Long programId;
    private String subject;
    private String detail;
    private LocalDate programStartDate;
    private LocalDate programFinishDate;
    private Integer capacity;
    private String programPlace;
    private Long totalParticipants;
    private List<ProgramCategoryResponse> categories;

    @QueryProjection // 생성자에 @QueryProjection 이 붙는다. 이후 빌드 툴을 이용해 compile 하면 Q타입의 클래스가 생성된다.
    public ProgramListResponse(String memberName, String institution, Major major, Lesson lesson, Long programId, String subject, String detail,
                               LocalDate programStartDate, LocalDate programFinishDate, Integer capacity, String programPlace, Long totalParticipants) {
        this.memberName = memberName;
        this.institution = institution;
        this.major = major;
        this.lesson = lesson;
        this.programId = programId;
        this.subject = subject;
        this.detail = detail;
        this.programStartDate = programStartDate;
        this.programFinishDate = programFinishDate;
        this.capacity = capacity;
        this.programPlace = programPlace;
        this.totalParticipants = totalParticipants;
    }
}
