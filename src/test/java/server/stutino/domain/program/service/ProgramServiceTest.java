package server.stutino.domain.program.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.stutino.domain.member.repository.MemberRepository;
import server.stutino.domain.program.repository.ParticipantsRepository;
import server.stutino.domain.program.repository.ProgramRepository;
import server.stutino.support.database.EnableDataBaseTest;

@EnableDataBaseTest
@DisplayName("ProgramService에서")
@Slf4j
public class ProgramServiceTest {
    @Mock
    private ParticipantsRepository participantsRepository;
    @Mock
    private ProgramRepository programRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ProgramService programService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    @DisplayName("특정 프로그램의 참여 내역이 존재할 경우 예외를 발생시키는가?")
//    void successParticipantDuplicateException() throws Exception {
//        // given
//        ProgramParticipateRequest request =
//                new ProgramParticipateRequest(
//                        1L,
//                        1L
//                );
//
//        // Mock 객체의 동작 정의
//        Program program = ProgramFixture.PROGRAM1.toEntity();
//        Member member = MemberFixture.MINCHANG.toEntity();
//
//        participantsRepository.save(Participants.builder()
//                .program(program)
//                .member(member)
//                .build());
//
//        // when
//
//        // then
//    }
}
