package tuk.mentor.domain.user.mentor.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tuk.mentor.auth.utils.CustomAuthorityUtils;
import tuk.mentor.domain.user.mentor.dto.request.MentorRegisterRequest;
import tuk.mentor.domain.user.mentor.entity.Mentor;
import tuk.mentor.domain.user.mentor.mapper.MentorMapper;
import tuk.mentor.domain.user.mentor.repository.MentorRepository;
import tuk.mentor.util.StringUtil;
import tuk.mentor.util.s3.manager.S3Manager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MentorService {
    CustomAuthorityUtils customAuthorityUtils;
    PasswordEncoder passwordEncoder;
    MentorRepository mentorRepository;
    MentorMapper mentorMapper;
    S3Manager s3Manager;
    /*
     *  멘토 등록
     * */
    @Transactional
    public void registerMentor(MentorRegisterRequest request, MultipartFile image, HttpServletRequest servletRequest) throws IOException {
        // [1] Mentor 기본 정보 저장
        // [1-1] GCP Storage profile image url
        String imgUrl = s3Manager.upload(image, s3Manager.getDirName(servletRequest));
        String encodePassword = passwordEncoder.encode(request.getPassword());

        // [1-2] 멘토 정보 매핑
        Mentor mentor = mentorMapper.toEntityFromRegisterRequest(request);
        mentor.setImgUrl(imgUrl);
        mentor.setPassword(encodePassword);
        mentor.setRoles(customAuthorityUtils.createMentorRole());

        System.out.println(StringUtil.toString(mentor));

        /*
        * todo User 객체의 userId 정보를 등록해야함.
        * */

        // [1-3] 멘토 정보 저장
        /*
         mentor entity의 Email 필드에 @Email 어노테이션을 붙이면 오류가 save() 안됨.
         Mentor [id=1, role=MENTOR, emaildbwpqls@naver.com, name=...]
         여기서 email = ? 으로 출력되지 않는게 이유가 될듯 함.
        * */
        mentorRepository.save(mentor);
    }
}