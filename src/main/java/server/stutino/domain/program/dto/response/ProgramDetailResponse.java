package server.stutino.domain.program.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import server.stutino.domain.member.entity.Lesson;
import server.stutino.domain.member.entity.Major;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ProgramDetailResponse {
    private Long mentorId;
    private String mentorName;
    private String institution;
    private Integer grade;
    private Major major;
    private Lesson lesson;
    private String introduce;
    private Long programId;
    private String subject;
    private String detail;
    private LocalDate programStartDate;
    private LocalDate programFinishDate;
    private LocalDate recruitStartDate;
    private LocalDate recruitFinishDate;
    private Integer capacity;
    private String programPlace;
    private List<ProgramWeekDetailResponse> programWeeks;

    @QueryProjection
    public ProgramDetailResponse(Long mentorId, String mentorName, String institution, Integer grade,
                                 Major major, Lesson lesson, String introduce, Long programId, String subject, String detail,
                                 LocalDate programStartDate, LocalDate programFinishDate, LocalDate recruitStartDate, LocalDate recruitFinishDate,
                                 Integer capacity, String programPlace) {
        this.mentorId = mentorId;
        this.mentorName = mentorName;
        this.institution = institution;
        this.grade = grade;
        this.major = major;
        this.lesson = lesson;
        this.introduce = introduce;
        this.programId = programId;
        this.subject = subject;
        this.detail = detail;
        this.programStartDate = programStartDate;
        this.programFinishDate = programFinishDate;
        this.recruitStartDate = recruitStartDate;
        this.recruitFinishDate = recruitFinishDate;
        this.capacity = capacity;
        this.programPlace = programPlace;
    }
}
