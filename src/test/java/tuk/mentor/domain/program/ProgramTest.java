package tuk.mentor.domain.program;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import tuk.mentor.domain.program.dto.response.ProgramDetailResponse;
import tuk.mentor.domain.program.repository.ProgramRepository;
import tuk.mentor.support.resolver.EntityManagerResolver;

import javax.transaction.Transactional;

@SpringBootTest
@ExtendWith(EntityManagerResolver.class)
@Transactional
@RequiredArgsConstructor
public class ProgramTest {
    private final ProgramRepository programRepository;

    @Test
    void getProgramListTest() {

    }

    @Test
    void getProgramDetail() {
        Long programId = 4l;
        ProgramDetailResponse response =
                programRepository.getProgramDetail(programId);

        assert(response.getProgramId().equals(programId));
    }
}
