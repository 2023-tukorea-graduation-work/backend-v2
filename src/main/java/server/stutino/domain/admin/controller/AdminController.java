package server.stutino.domain.admin.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.stutino.domain.admin.dto.request.PassedMentorRequest;
import server.stutino.domain.admin.dto.response.MemberListResponse;
import server.stutino.domain.admin.service.AdminService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    AdminService adminService;

    /*
     * 관리자 페이지 회원 목록 조회
     * */
    @GetMapping
    public ResponseEntity<List<MemberListResponse>> findAllMember() {
        return ResponseEntity.ok().body(adminService.findAllMember());
    }

    /*
    * 멘토 등록 승인/거부
    * */
    @PostMapping
    public ResponseEntity<Void> registerPassedMentor(@Valid @RequestBody PassedMentorRequest request) {
        adminService.registerPassedMentor(request);
        return ResponseEntity.ok().build();
    }
}