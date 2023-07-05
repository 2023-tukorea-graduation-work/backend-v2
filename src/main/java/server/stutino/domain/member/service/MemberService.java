package server.stutino.domain.member.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import server.stutino.auth.utils.CustomAuthorityUtils;
import server.stutino.domain.member.dto.request.MenteeRegisterRequest;
import server.stutino.domain.member.dto.request.MentorRegisterRequest;
import server.stutino.domain.member.entity.Member;
import server.stutino.domain.member.repository.MemberRepository;
import server.stutino.util.CustomStringUtil;
import server.stutino.util.s3.manager.S3Manager;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberService {
    CustomAuthorityUtils customAuthorityUtils;
    PasswordEncoder passwordEncoder;
    MemberRepository memberRepository;
    S3Manager s3Manager;

    /*
     *  멘토 등록
     * */
    @Transactional
    public void registerMentor(MentorRegisterRequest request, MultipartFile image) {
        // [1] Mentor 기본 정보 저장
        // [1-1] GCP Storage profile image url
        String imgUrl = "";
        try {
            imgUrl = s3Manager.upload(image, "/profile-image");
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
            /*
             * todo: JPA의 등록을 하면서 처리중 문제가 생기면 등록 자체를 안하는 문제가 있는듯
             * */
            // throw new IOException("Failed to upload image: " + e.getMessage());
        }

        String encodePassword = passwordEncoder.encode(request.getPassword());

        // [1-2] 멘토 정보 매핑
        Member mentor = Member.builder()
                .email(request.getEmail())
                .password(encodePassword)
                .roles(customAuthorityUtils.createMentorRole())
                .name(request.getName())
                .age(request.getAge())
                .institution(request.getCollege())
                .grade(request.getGrade())
                .major(request.getMajor())
                .lesson(request.getLesson())
                .introduce(request.getIntroduce())
                .imgUrl(imgUrl)
                .build();

        // [1-3] 멘토 정보 저장
        /*
         mentor entity의 Email 필드에 @Email 어노테이션을 붙이면 오류가 save() 안됨.
        * */
        memberRepository.save(mentor);
    }

    /*
     *  멘토 등록
     * */
    public void registerMentee(MenteeRegisterRequest request, MultipartFile image) {
        System.out.println(CustomStringUtil.toString(request));

        // [1] Mentor 기본 정보 저장
        // [1-1] GCP Storage profile image url
        String imgUrl = "";
        try {
            imgUrl = s3Manager.upload(image, "/profile-image");
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
            /*
             * todo: JPA의 등록을 하면서 처리중 문제가 생기면 등록 자체를 안하는 문제가 있는듯
             * */
            // throw new IOException("Failed to upload image: " + e.getMessage());
        }

        String encodePassword = passwordEncoder.encode(request.getPassword());

        // [1-2] 멘토 정보 매핑
        Member mentee = Member.builder()
                .email(request.getEmail())
                .password(encodePassword)
                .roles(customAuthorityUtils.createMentorRole())
                .name(request.getName())
                .age(request.getAge())
                .institution(request.getSchool())
                .grade(request.getGrade())
                .introduce(request.getIntroduce())
                .imgUrl(imgUrl)
                .build();

        // [1-3] 멘토 정보 저장
        memberRepository.save(mentee);

    }
}