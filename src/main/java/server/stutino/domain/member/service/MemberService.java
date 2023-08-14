package server.stutino.domain.member.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.stutino.auth.utils.CustomAuthorityUtils;
import server.stutino.domain.member.dto.request.MenteeRegisterRequest;
import server.stutino.domain.member.dto.request.MentorRegisterRequest;
import server.stutino.domain.member.dto.response.MyPageResponse;
import server.stutino.domain.member.dto.response.MyProgramResponse;
import server.stutino.domain.member.entity.Member;
import server.stutino.domain.member.exception.EmailDuplicateException;
import server.stutino.domain.member.repository.MemberRepository;
import server.stutino.domain.program.entity.Program;
import server.stutino.domain.program.repository.ProgramRepository;
import server.stutino.util.CustomDateUtil;
import server.stutino.util.s3.manager.S3Manager;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberService {
    CustomAuthorityUtils customAuthorityUtils;
    PasswordEncoder passwordEncoder;
    MemberRepository memberRepository;
    ProgramRepository programRepository;
    S3Manager s3Manager;
    CustomDateUtil dateUtil;

    /*
     *  멘토 등록
     * */
//    @Transactional
    public void registerMentor(MentorRegisterRequest request, MultipartFile image, MultipartFile certification) {
        // email duplicate check
        if(memberRepository.findByEmail(request.getEmail()) != null) {
            System.out.println("email duplicated");
            throw new EmailDuplicateException();
        }
        else {
            // [1] Mentor 기본 정보 저장
            // [1-1] GCP Storage profile image url
            String imgUrl = "", certificateUrl = "";
            try {
                imgUrl = s3Manager.upload(image, "profile-image");
                System.out.println(imgUrl);
                certificateUrl = s3Manager.upload(certification, "mentor-certification");
                System.out.println(certificateUrl);

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
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
    }

    /*
     *  멘티 등록
     * */
    public void registerMentee(MenteeRegisterRequest request, MultipartFile image) {
        if(memberRepository.findByEmail(request.getEmail()) != null) {
            throw new EmailDuplicateException();
        }
        // [1] Mentor 기본 정보 저장
        // [1-1] GCP Storage profile image url
        String imgUrl = "";
        try {
            imgUrl = s3Manager.upload(image, "profile-image");
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
                .isPassed(true)
                .build();

        // [1-3] 멘토 정보 저장
        memberRepository.save(mentee);
    }

    /*
    * 마이페이지 정보 조회
    * */
    public MyPageResponse getMyPageInfo(Long memberId) {
        // [1] Member 객체 조회
        Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);

        List<Program> programList;
        // 멘토인 경우
        if(member.getRoles().contains("ROLE_MENTOR")) {
            programList = programRepository.findProgramForMentor(memberId);
        }
        else {
            programList = programRepository.findProgramForMentee(memberId);
        }

        List<MyProgramResponse> myprogramList = programList.stream().map(program ->
                MyProgramResponse.builder()
                        .programId(program.getId())
                        .mentorName(program.getMember().getName())
                        .mentorInstitution(program.getMember().getInstitution())
                        .mentorMajor(program.getMember().getMajor())
                        .subject(program.getSubject())
                        .programPlace(program.getProgramPlace())
                        .capacity(program.getCapacity())
                        .recruitPeriod(dateUtil.localDateToString(program.getRecruitStartDate()) + " ~ " +
                                dateUtil.localDateToString(program.getRecruitFinishDate()))
                        .programPeriod(dateUtil.localDateToString(program.getProgramStartDate()) + " ~ " +
                                dateUtil.localDateToString(program.getProgramFinishDate()))
                        .state(program.getProgramState())
                        .build()).toList();

        // [3] dto mapping
        return new MyPageResponse(
                member.getName(),
                member.getAge(),
                member.getEmail(),
                member.getImgUrl(),
                myprogramList);
    }
}