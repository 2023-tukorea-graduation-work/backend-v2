package server.stutino.domain.question.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import server.stutino.domain.question.dto.response.QQuestionListResponse;
import server.stutino.domain.question.dto.response.QuestionListResponse;

import java.util.List;

import static server.stutino.domain.program.entity.QParticipants.participants;
import static server.stutino.domain.program.entity.QProgram.program;
import static server.stutino.domain.question.entity.QQuestion.question;


@Repository
@RequiredArgsConstructor
public class QuestionQueryRepositoryImpl implements QuestionQueryRepository {
    private final JPAQueryFactory queryFactory;

    /*
    * 특정 프로그램 내 등록된 모든 질문 조회
    * */
    @Override
    public List<QuestionListResponse> getAllQuestionByProgramId(Long programId) {

        return queryFactory
                .select(new QQuestionListResponse(
                        question.id,
                        question.questions,
                        question.answers,
                        question.answerCreatedAt,
                        question.answerUpdatedAt
                ))
                .from(program)
                .where(question.id.in(findParticipantsIds(programId)))
                .fetch();
    }

    /*
    * 특정 프로그램에 참여한 참여자 번호 조회
    * */
    private List<Long> findParticipantsIds(Long programId) {
        return queryFactory
                .select(participants.id)
                .from(participants)
                .innerJoin(participants.program, program)
                .where(participants.program.id.eq(programId))
                .fetch();
    }
}
