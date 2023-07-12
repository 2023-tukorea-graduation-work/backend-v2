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
import server.stutino.util.s3.manager.S3Manager;

import java.io.IOException;

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
    public void registerMentor(MentorRegisterRequest request, MultipartFile image, MultipartFile certification) {
        // [1] Mentor 기본 정보 저장
        // [1-1] GCP Storage profile image url
        String imgUrl = "", certificateUrl = "";
        try {
            imgUrl = s3Manager.upload(image, "/profile-image");
            certificateUrl = s3Manager.upload(image, "/mentor-certification");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                .certificateUrl(certificateUrl)
                .build();

        // [1-3] 멘토 정보 저장
        /*
         mentor entity의 Email 필드에 @Email 어노테이션을 붙이면 오류가 save() 안됨.
        * */
        memberRepository.save(mentor);
    }

    /*
     *  멘티 등록
     * */
    public void registerMentee(MenteeRegisterRequest request, MultipartFile image) {
        // [1] Mentor 기본 정보 저장
        // [1-1] GCP Storage profile image url
        String imgUrl = "";
        try {
            imgUrl = s3Manager.upload(image, "/profile-image");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String encodePassword = passwordEncoder.encode(request.getPassword());

        // [1-2] 멘토 정보 매핑
        Member mentee = Member.builder()
                .email(request.getEmail())
                .password(encodePassword)
                .roles(customAuthorityUtils.createMenteeRole())
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