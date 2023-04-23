package tuk.mentor.domain.program.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;;
import tuk.mentor.domain.mentor.entity.Lesson;
import tuk.mentor.domain.mentor.entity.Major;
import java.time.LocalDate;

@Data
public class ProgramListResponse {
    private String name;
    private String college;
    private Major major;
    private Lesson lesson;
    private Long id;
    private String subject;
    private String detail;
    private LocalDate programStartDate;
    private LocalDate programFinishDate;
    private Integer capacity;
    private String programPlace;
    private Long participant;

    @QueryProjection // 생성자에 @QueryProjection 이 붙는다. 이후 빌드 툴을 이용해 compile 하면 Q타입의 클래스가 생성된다.
    public ProgramListResponse(String name, String college, Major major, Lesson lesson, Long id, String subject, String detail, LocalDate programStartDate, LocalDate programFinishDate, Integer capacity, String programPlace, Long participant) {
        this.name = name;
        this.college = college;
        this.major = major;
        this.lesson = lesson;
        this.id = id;
        this.subject = subject;
        this.detail = detail;
        this.programStartDate = programStartDate;
        this.programFinishDate = programFinishDate;
        this.capacity = capacity;
        this.programPlace = programPlace;
        this.participant = participant;
    }
}
