package server.stutino.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import server.stutino.domain.member.entity.Major;
import server.stutino.domain.program.entity.ProgramState;

@Data
@Builder
@AllArgsConstructor
public class MyProgramResponse {
    private Long programId;
    private String mentorName;
    private String mentorInstitution;
    private Major mentorMajor;
    private String subject;
    private String programPlace;
    private Integer capacity;
    private String recruitPeriod;
    private String programPeriod;
    private ProgramState state;

//    @QueryProjection
//    public MyProgramResponse(String mentorName, String mentorInstitution, Major mentorMajor, String subject, String programPlace, Integer capacity, String recruitPeriod, String programPeriod, ProgramState state) {
//        this.mentorName = mentorName;
//        this.mentorInstitution = mentorInstitution;
//        this.mentorMajor = mentorMajor;
//        this.subject = subject;
//        this.programPlace = programPlace;
//        this.capacity = capacity;
//        this.recruitPeriod = recruitPeriod;
//        this.programPeriod = programPeriod;
//        this.state = state;
//    }
}
