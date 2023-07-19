package server.stutino.domain.program.repository;

import server.stutino.domain.program.dto.response.ProgramDetailResponse;
import server.stutino.domain.program.dto.response.ProgramListResponse;

import java.util.List;

public interface ProgramQueryRepository {
    List<ProgramListResponse> getProgramList(String keyword);
    ProgramDetailResponse getProgramDetail(Long programId);
//    List<MyProgramResponse> getMyProgram(Long memberId);
}
