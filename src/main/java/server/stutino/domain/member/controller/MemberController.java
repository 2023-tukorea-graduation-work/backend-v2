package server.stutino.domain.member.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.stutino.domain.member.dto.request.MenteeRegisterRequest;
import server.stutino.domain.member.dto.request.MentorRegisterRequest;
import server.stutino.domain.member.service.MemberService;
import server.stutino.util.SuccessResponse;

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
    public ResponseEntity<SuccessResponse> registerMentor(
            @RequestPart(value = "data", required = true) MentorRegisterRequest mentorRegisterRequest,
            @RequestPart(value = "file", required = false) MultipartFile image) {
        memberService.registerMentor(mentorRegisterRequest, image);
        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK, "success register mentor"));
    }

    /*
     * 멘티 등록
     * */
    @PostMapping("/mentee")
    public ResponseEntity<SuccessResponse> registerMentee(
            @RequestPart(value = "data", required = true) MenteeRegisterRequest menteeRegisterRequest,
            @RequestPart(value = "file", required = false) MultipartFile image) {
        memberService.registerMentee(menteeRegisterRequest, image);
        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK, "success register mentee"));
    }

}