package server.stutino.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import server.stutino.util.s3.manager.S3Manager;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class MemberServiceTest {
    @Autowired private MemberService memberService;
    @Autowired private S3Manager s3Manager;

    @Test
    @DisplayName("s3에 이미지를 성공적으로 올리는가?")
    void successUploadS3Image() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                MediaType.MULTIPART_FORM_DATA_VALUE, "Hello, World!".getBytes());

        // when
        String expected = s3Manager.upload(file, "/profile-image");

        // then
        assertNotEquals(expected, "");
    }
}
