package server.stutino.domain.member.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.stutino.domain.member.dto.request.MenteeRegisterRequest;
import server.stutino.domain.member.dto.request.MentorRegisterRequest;
import server.stutino.domain.member.dto.response.MyPageResponse;
import server.stutino.domain.member.service.MemberService;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberController {
    MemberService memberService;

    /*
     * 멘토 등록
     * */
    @PostMapping("/mentor")
    public ResponseEntity<Void> registerMentor(
            @RequestPart(value = "data", required = true) @Valid MentorRegisterRequest mentorRegisterRequest,
            @RequestPart(value = "image", required = false) @Valid MultipartFile image,
            @RequestPart(value = "certification", required = false) @Valid MultipartFile certification) {
        memberService.registerMentor(mentorRegisterRequest, image, certification);
        return ResponseEntity.ok().build();
    }

    /*
     * 멘티 등록
     * */
    @PostMapping("/mentee")
    public ResponseEntity<Void> registerMentee(
            @RequestPart(value = "data", required = true) @Valid MenteeRegisterRequest menteeRegisterRequest,
            @RequestPart(value = "image", required = false) @Valid MultipartFile image) {
        memberService.registerMentee(menteeRegisterRequest, image);
        return ResponseEntity.ok().build();
    }

    /*
    * 마이페이지 정보 조회
    * */
    @GetMapping("/my-page/{memberId}")
    public ResponseEntity<MyPageResponse> getMyPageInfo(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(memberService.getMyPageInfo(memberId));
    }
}