package tuk.mentor.domain.program.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.program.dto.response.ProgramDetailResponse;
import tuk.mentor.domain.program.dto.response.ProgramListResponse;
import tuk.mentor.domain.program.dto.response.QProgramDetailResponse;
import tuk.mentor.domain.program.dto.response.QProgramListResponse;

import java.util.List;

import static tuk.mentor.domain.mentor.entity.QMentor.mentor;
import static tuk.mentor.domain.program.entity.QProgram.program;
import static tuk.mentor.domain.program.entity.QProgramParticipation.programParticipation;


@Repository
@RequiredArgsConstructor
public class ProgramQueryRepositoryImpl implements ProgramQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProgramListResponse> getProgramList(String keyword) {

        return queryFactory
                .select(new QProgramListResponse(
                        mentor.name,
                        mentor.college,
                        mentor.major,
                        mentor.lesson,
                        program.id,
                        program.subject,
                        program.detail,
                        program.programStartDate,
                        program.programFinishDate,
                        program.capacity,
                        program.programPlace,
                        queryFactory
                                .select(programParticipation.count().as("participant"))
                                .from(programParticipation)
                                .where(programParticipation.program.id.eq(program.id))
                ))
                .from(program)
                .join(program.mentor, mentor)
                .fetch();
    }

    @Override
    public ProgramDetailResponse getProgramDetail(Long programId) {
        return queryFactory
                .select(new QProgramDetailResponse(
                        mentor.id,
                        mentor.name,
                        mentor.college,
                        mentor.grade,
                        mentor.major,
                        mentor.lesson,
                        mentor.introduce,
                        program.id,
                        program.subject,
                        program.detail,
                        program.programStartDate,
                        program.programFinishDate,
                        program.recruitStartDate,
                        program.recruitFinishDate,
                        program.capacity,
                        program.programPlace
                        ))
                .from(program)
                .join(program.mentor, mentor)
                .where(program.id.eq(programId))
                .fetchOne();
    }
}
