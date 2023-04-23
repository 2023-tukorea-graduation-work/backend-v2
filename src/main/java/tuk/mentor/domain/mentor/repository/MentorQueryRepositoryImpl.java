package tuk.mentor.domain.mentor.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tuk.mentor.domain.mentor.entity.Mentor;

import static tuk.mentor.domain.mentor.entity.QMentor.mentor;


@Repository
@RequiredArgsConstructor
public class MentorQueryRepositoryImpl implements MentorQueryRepository {
    private final JPAQueryFactory queryFactory;

}
