package server.stutino.domain.program.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import server.stutino.domain.program.dto.response.ProgramDetailResponse;
import server.stutino.domain.program.dto.response.ProgramListResponse;
import server.stutino.domain.program.dto.response.QProgramDetailResponse;
import server.stutino.domain.program.dto.response.QProgramListResponse;
import server.stutino.util.CustomDateUtil;

import java.util.List;

import static server.stutino.domain.member.entity.QMember.member;
import static server.stutino.domain.program.entity.QParticipants.participants;
import static server.stutino.domain.program.entity.QProgram.program;


@Repository
@RequiredArgsConstructor
public class ProgramQueryRepositoryImpl implements ProgramQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final CustomDateUtil dateUtil;

    @Override
    public List<ProgramListResponse> getProgramList(String keyword) {

        return queryFactory
                .select(new QProgramListResponse(
                        member.name,
                        member.institution,
                        member.major,
                        member.lesson,
                        program.id,
                        program.subject,
                        program.detail,
                        program.programStartDate,
                        program.programFinishDate,
                        program.capacity,
                        program.programPlace,
                        queryFactory
                                .select(participants.count().as("participant"))
                                .from(participants)
                                .where(participants.program.id.eq(program.id))
                ))
                .from(program)
                .join(program.member, member)
                .fetch();
    }

    @Override
    public ProgramDetailResponse getProgramDetail(Long programId) {
        return queryFactory
                .select(new QProgramDetailResponse(
                        member.id,
                        member.name,
                        member.institution,
                        member.grade,
                        member.major,
                        member.lesson,
                        member.introduce,
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
                .join(program.member, member)
                .where(program.id.eq(programId))
                .fetchOne();
    }

//    @Override
//    public List<MyProgramResponse> getMyProgram(Long memberId) {
//        return queryFactory
//                .select(new QMyProgramResponse(
//                        member.name,
//                        member.institution,
//                        member.major,
//                        program.subject,
//                        program.programPlace,
//                        program.capacity,
//                        dateUtil.localDateToString(program.recruitStartDate) + "~" + dateUtil.localDateToString(program.recruitFinishDate),
//                        dateUtil.localDateToString(program.recruitStartDate) + "~" + dateUtil.localDateToString(program.recruitFinishDate),
//                        program.programState
//                ))
//                .from(program)
//                .join(program.member, member)
//                .where(program.member.id.eq(memberId))
//                .fetch();
//    }
}
