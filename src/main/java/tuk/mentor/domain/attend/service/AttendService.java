package tuk.mentor.domain.attend.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import tuk.mentor.domain.attend.dto.response.AttendListByProgramResponse;
import tuk.mentor.domain.attend.repository.AttendRepository;
import tuk.mentor.domain.program.entity.ProgramParticipation;
import tuk.mentor.domain.program.repository.ProgramParticipationRepository;
import tuk.mentor.domain.program.repository.ProgramRepository;
import tuk.mentor.domain.user.mentee.entity.Mentee;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendService {
    ProgramParticipationRepository programParticipationRepository;
    AttendRepository attendRepository;

/*    public AttendListByProgramResponse getAttendanceListByProgram(Long programId) {
        // [1] 프로그램에 등록된 모든 멘티 정보 조회
        List<Mentee> mentees = programParticipationRepository.findParticipantByProgramId(programId);
        
        // [2] 멘티별 출결 상태 목록 조회
    }*/
}


