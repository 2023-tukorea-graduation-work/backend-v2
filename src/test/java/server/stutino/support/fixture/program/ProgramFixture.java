package server.stutino.support.fixture.program;

import lombok.Getter;
import server.stutino.domain.member.entity.Member;
import server.stutino.domain.program.entity.Program;
import server.stutino.support.fixture.member.MemberFixture;

import java.time.LocalDate;

@Getter
public enum ProgramFixture {
    PROGRAM1(
            MemberFixture.MINCHANG.toEntity(),
            "프로그램 제목 1",
            "프로그램 상세 정보 1",
            LocalDate.now(),
            LocalDate.now(),
            LocalDate.now(),
            LocalDate.now(),
            10,
            "학교");

    private Member member;
    private String subject;
    private String detail;
    private LocalDate programStartDate;
    private LocalDate programFinishDate;
    private LocalDate recruitStartDate;
    private LocalDate recruitFinishDate;
    private Integer capacity;
    private String programPlace;

    ProgramFixture(Member member, String subject, String detail, LocalDate programStartDate, LocalDate programFinishDate, LocalDate recruitStartDate, LocalDate recruitFinishDate, Integer capacity, String programPlace) {
        this.member = member;
        this.subject = subject;
        this.detail = detail;
        this.programStartDate = programStartDate;
        this.programFinishDate = programFinishDate;
        this.recruitStartDate = recruitStartDate;
        this.recruitFinishDate = recruitFinishDate;
        this.capacity = capacity;
        this.programPlace = programPlace;
    }

    public Program toEntity() {
        return Program.builder()
                .member(member)
                .subject(subject)
                .build();
    }
}
