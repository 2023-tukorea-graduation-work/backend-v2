package tuk.mentor.domain.user.mentor.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class MentorQueryRepositoryImpl implements MentorQueryRepository {
    private final JPAQueryFactory queryFactory;

}
