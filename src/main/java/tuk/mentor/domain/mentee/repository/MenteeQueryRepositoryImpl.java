package tuk.mentor.domain.mentee.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.mentee.entity.Mentee;

import static tuk.mentor.domain.mentee.entity.QMentee.mentee;


@Repository
@RequiredArgsConstructor
public class MenteeQueryRepositoryImpl implements MenteeQueryRepository {
    private final JPAQueryFactory queryFactory;

}
