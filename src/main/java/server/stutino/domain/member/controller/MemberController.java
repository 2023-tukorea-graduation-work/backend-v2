package server.stutino.domain.member.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import server.stutino.domain.member.dto.request.MenteeRegisterRequest;
import server.stutino.domain.member.dto.request.MentorRegisterRequest;
import server.stutino.domain.member.service.MemberService;

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
            @RequestPart(value = "data", required = true) MentorRegisterRequest mentorRegisterRequest,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "certification", required = false) MultipartFile certification) {
        memberService.registerMentor(mentorRegisterRequest, image, certification);
        return ResponseEntity.noContent().build();
    }

    /*
     * 멘티 등록
     * */
    @PostMapping("/mentee")
    public ResponseEntity<Void> registerMentee(
            @RequestPart(value = "data", required = true) MenteeRegisterRequest menteeRegisterRequest,
            @RequestPart(value = "file", required = false) MultipartFile image) {
        memberService.registerMentee(menteeRegisterRequest, image);
        return ResponseEntity.noContent().build();
    }
}