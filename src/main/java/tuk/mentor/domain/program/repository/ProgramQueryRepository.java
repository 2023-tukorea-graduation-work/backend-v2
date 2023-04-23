package tuk.mentor.domain.program.repository;

import tuk.mentor.domain.program.dto.response.ProgramDetailResponse;
import tuk.mentor.domain.program.dto.response.ProgramListResponse;

import java.util.List;

public interface ProgramQueryRepository {
    List<ProgramListResponse> getProgramList(String keyword);
    ProgramDetailResponse getProgramDetail(Long programId);
}
