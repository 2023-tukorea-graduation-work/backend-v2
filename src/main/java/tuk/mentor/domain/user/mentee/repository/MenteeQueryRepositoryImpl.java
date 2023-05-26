package tuk.mentor.domain.user.mentee.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class MenteeQueryRepositoryImpl implements MenteeQueryRepository {
    private final JPAQueryFactory queryFactory;

}
