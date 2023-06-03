package tuk.mentor.domain.user.mentee.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tuk.mentor.auth.utils.CustomAuthorityUtils;
import tuk.mentor.domain.user.mentee.dto.request.MenteeRegisterRequest;
import tuk.mentor.domain.user.mentee.entity.Mentee;
import tuk.mentor.domain.user.mentee.mapper.MenteeMapper;
import tuk.mentor.domain.user.mentee.repository.MenteeRepository;
import tuk.mentor.util.s3.manager.S3Manager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class MenteeService {
    CustomAuthorityUtils customAuthorityUtils;
    PasswordEncoder passwordEncoder;
    MenteeRepository menteeRepository;
    MenteeMapper menteeMapper;
    S3Manager s3Manager;
    /*
    *  멘토 등록
    * */
    @Transactional
    public void registerMentee(MenteeRegisterRequest request, MultipartFile image, HttpServletRequest servletRequest) throws IOException {
        // [1] Mentor 기본 정보 저장
        // [1-1] GCP Storage profile image url
        String imgUrl = "";
        try {
            imgUrl = s3Manager.upload(image, s3Manager.getDirName(servletRequest));
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
            // throw new IOException("Failed to upload image: " + e.getMessage());
        }
        String encodePassword = passwordEncoder.encode(request.getPassword());

        // [1-2] 멘토 정보 매핑
        Mentee mentee = menteeMapper.toEntityFromRegisterRequest(request);
        mentee.setImgUrl(imgUrl);
        mentee.setPassword(encodePassword);
        mentee.setRoles(customAuthorityUtils.createMenteeRole());

        // [1-2] 멘티 정보 저장
        menteeRepository.save(mentee);
    }
}
