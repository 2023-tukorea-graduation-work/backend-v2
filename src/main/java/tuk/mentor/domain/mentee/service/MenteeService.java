package tuk.mentor.domain.mentee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tuk.mentor.domain.mentee.dto.request.MenteeRegisterRequest;
import tuk.mentor.domain.mentee.dto.response.MenteeRegisterResponse;
import tuk.mentor.domain.mentee.entity.Mentee;
import tuk.mentor.domain.mentee.mapper.MenteeMapper;
import tuk.mentor.domain.mentee.repository.MenteeRepository;
import tuk.mentor.global.s3.manager.S3Manager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenteeService {
    private final MenteeRepository menteeRepository;
    private final MenteeMapper menteeMapper;
    @PersistenceContext
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final S3Manager s3Manager;
    /*
    *  멘토 등록
    * */
    @Transactional
    public MenteeRegisterResponse registerMentee(MenteeRegisterRequest request, MultipartFile image) throws IOException {

        // [1] Mentor 기본 정보 저장
        Mentee mentee = menteeMapper.toEntityFromRegisterRequest(request);

        // [1-1] 비밀번호 암호화
        mentee.setPassword(passwordEncoder.encode(request.getPassword()));

        // [1-2] GCP Storage profile image url
        String url = s3Manager.upload(image, s3Manager.getDirName());
        mentee.setImgUrl(url);

        // [1-2] 멘티 정보 저장
        mentee = menteeRepository.save(mentee);

        return MenteeRegisterResponse.builder()
                .id(mentee.getId())
                .role(mentee.getRole())
                .build();
    }

    public Optional<Mentee> findByEmail(String email) {
        return menteeRepository.findByEmail(email);
    }
}
