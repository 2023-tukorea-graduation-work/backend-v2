package tuk.mentor.domain.mentor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tuk.mentor.domain.mentor.dto.request.MentorRegisterRequest;
import tuk.mentor.domain.mentor.dto.response.MentorRegisterResponse;
import tuk.mentor.domain.mentor.entity.Mentor;
import tuk.mentor.domain.mentor.mapper.MentorMapper;
import tuk.mentor.domain.mentor.repository.MentorRepository;
import tuk.mentor.global.s3.manager.S3Manager;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MentorService {
    private final MentorRepository mentorRepository;
    private final MentorMapper mentorMapper;
    private final PasswordEncoder passwordEncoder;
    private final S3Manager s3Manager;
    /*
    *  멘토 등록
    * */
    @Transactional
    public MentorRegisterResponse registerMentor(MentorRegisterRequest request, MultipartFile image) throws IOException {
        // [1] Mentor 기본 정보 저장
        Mentor mentor = mentorMapper.toEntityFromRegisterRequest(request);

        // [1-1] 비밀번호 암호화
        mentor.setPassword(passwordEncoder.encode(request.getPassword()));

        // [1-2] GCP Storage profile image url
        String url = s3Manager.upload(image, s3Manager.getDirName());
        mentor.setImgUrl(url);

        // [1-2] 멘토 정보 저장
        /*
         mentor entity의 Email 필드에 @Email 어노테이션을 붙이면 오류가 save() 안됨.
         Mentor [id=1, role=MENTOR, emaildbwpqls@naver.com, name=...]
         여기서 email = ? 으로 출력되지 않는게 이유가 될듯 함.
        * */
        mentor = mentorRepository.save(mentor);

        return MentorRegisterResponse.builder()
                .id(mentor.getId())
                .role(mentor.getRole())
                .build();
    }

    public Optional<Mentor> findByEmail(String email) {
        return mentorRepository.findByEmail(email);
    }
}
