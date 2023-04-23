package tuk.mentor.domain.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuk.mentor.domain.mentee.repository.MenteeRepository;
import tuk.mentor.domain.mentor.entity.Mentor;
import tuk.mentor.domain.mentor.repository.MentorRepository;
import tuk.mentor.domain.program.dto.request.ProgramParticipateRequest;
import tuk.mentor.domain.program.dto.request.ProgramRegisterRequest;
import tuk.mentor.domain.program.dto.response.ProgramDetailResponse;
import tuk.mentor.domain.program.dto.response.ProgramListResponse;
import tuk.mentor.domain.program.entity.Program;
import tuk.mentor.domain.program.entity.ProgramParticipation;
import tuk.mentor.domain.program.entity.ProgramWeek;
import tuk.mentor.domain.program.mapper.ProgramMapper;
import tuk.mentor.domain.program.repository.ProgramParticipationRepository;
import tuk.mentor.domain.program.repository.ProgramRepository;
import tuk.mentor.domain.program.repository.ProgramWeekRepository;
import tuk.mentor.global.login.LoginInfo;
import tuk.mentor.global.util.DateUtil;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final ProgramWeekRepository programWeekRepository;
    private final ProgramParticipationRepository programParticipationRepository;
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;
    private final ProgramMapper programMapper;
    private final DateUtil dateUtil;

    /*
     * 프로그램 등록
     * */
    @Transactional
    public void registerProgram(ProgramRegisterRequest request, HttpServletRequest httpServletRequest) {
        // [1] 프로그램 기본 정보 등록
        // [1-1] 세션에 등록된 로그인 정보 조회 및 멘토 조회
        HttpSession session = httpServletRequest.getSession();
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Mentor mentor = mentorRepository.findById(loginInfo.getUserID()).orElseThrow(RuntimeException::new);

        /*
         * 문제1. programMapper.toEntity(request, mentor, programWeeks)를 하면 program.id 가 null이 아님.
         * programRepository.save(program); 시 insert전에 select를 먼저 함. 이때 일치하는 program 정보가 있는 탓인지 insert를 안하는 문제가 있음.
         * 문제2. program_week 테이블의 program_id(fk)도 insert되지 않는 문제가 있다.
         *
         * 해결1 : select -> insert문제는 해결 못함
         * 해결2 : program_week 테이블의 program_id(fk)도 insert되지 않는 문제는 saveAll로 해결
         * */
        Program program = programRepository.save(
                Program.builder()
                    .mentor(mentor)
                    .subject(request.getSubject())
                        .detail(request.getDetail())
                        .programStartDate(dateUtil.convertStringToLocalDate(request.getProgramStartDate()))
                        .programFinishDate(dateUtil.convertStringToLocalDate(request.getProgramFinishDate()))
                        .recruitStartDate(dateUtil.convertStringToLocalDate(request.getRecruitStartDate()))
                        .recruitFinishDate(dateUtil.convertStringToLocalDate(request.getRecruitFinishDate()))
                        .capacity(request.getCapacity())
                        .programPlace(request.getProgramPlace())
                    .build()
        );

        // [1-2] Program Entity map
        Set<ProgramWeek> programWeeks = request.getProgramWeeks().stream().map(programWeek -> ProgramWeek.builder()
                .program(program)
                .content(programWeek.getContent())
                .programWeekStartDate(dateUtil.convertStringToLocalDate(programWeek.getProgramWeekStartDate()))
                .programWeekFinishDate(dateUtil.convertStringToLocalDate(programWeek.getProgramWeekFinishDate()))
                .build()).collect(Collectors.toSet());

        // [1-3] Program & ProgramWeeks 기본 정보 등록
        programWeekRepository.saveAll(programWeeks);

    }

    /*
     * 프로그램 목록 조회
     * */
    public List<ProgramListResponse> getProgramList(String keyword) {
        return programRepository.getProgramList(keyword);
    }

    /*
    * 프로그램 상세 조회
    * */
    public ProgramDetailResponse getProgramDetail(Long programId) {
        ProgramDetailResponse response = programRepository.getProgramDetail(programId);
        List<ProgramWeek> programWeeks = programWeekRepository.getProgramWeekByProgramId(programId);
        response.setProgramWeeks(programMapper.toProgramWeekDetailDto(programWeeks));
        return response;
    }

    /*
    * 프로그램 참여 정보 등록
    * */
    public void registerParticipation(ProgramParticipateRequest request) {
        programParticipationRepository.save(ProgramParticipation.builder()
                .program(programRepository.findById(request.getProgramId()).orElseThrow(EntityNotFoundException::new))
                .mentee(menteeRepository.findById(request.getMenteeId()).orElseThrow(EntityNotFoundException::new))
                .build());
    }
}


