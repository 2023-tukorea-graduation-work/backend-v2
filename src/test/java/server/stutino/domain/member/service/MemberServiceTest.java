package server.stutino.domain.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import server.stutino.util.s3.manager.S3Manager;

@SpringBootTest
public class MemberServiceTest {
    @Autowired private MemberService memberService;
    @Autowired private S3Manager s3Manager;

//    @Test
//    @DisplayName("s3에 이미지를 성공적으로 올리는가?")
//    void successUploadS3Image() throws Exception {
//        // given
//        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
//                MediaType.MULTIPART_FORM_DATA_VALUE, "Hello, World!".getBytes());
//
//        // when
//        String expected = s3Manager.upload(file, "/profile-image");
//
//        // then
//        assertNotEquals(expected, "");
//    }
}
