package server.stutino.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import server.stutino.auth.service.AuthService;
import server.stutino.domain.member.Member;
import server.stutino.domain.member.repository.MemberRepository;
import server.stutino.support.database.EnableDataBaseTest;

@EnableDataBaseTest
public abstract class LoginTest {
    @MockBean private AuthService authService;

    @Autowired private MemberRepository memberRepository;

    protected Member loginUser;

//    @BeforeEach
//    private void setup() {
//        Mentor member = MentorFixture.MINCHANG.toEntity();
//        loginUser = mentorRepository.save(member);
//        when(authService.getLoginUser()).thenReturn(loginUser);
//    }
}
