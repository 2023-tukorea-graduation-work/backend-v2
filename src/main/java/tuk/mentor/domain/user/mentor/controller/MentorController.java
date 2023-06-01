package tuk.mentor.domain.user.mentor.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tuk.mentor.domain.user.User;
import tuk.mentor.domain.user.mentor.dto.request.MentorRegisterRequest;
import tuk.mentor.domain.user.mentor.dto.response.MentorRegisterResponse;
import tuk.mentor.domain.user.mentor.service.MentorService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/mentor")
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;

    @PostMapping
    public ResponseEntity<MentorRegisterResponse> registerMentor(@ModelAttribute MentorRegisterRequest mentorRegisterRequest,
                                                                 @RequestPart(value = "file", required = false) MultipartFile image,
                                                                HttpServletRequest servletRequest) throws IOException {
        MentorRegisterResponse response = mentorService.registerMentor(mentorRegisterRequest, image, servletRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping()
    public ResponseEntity<User> registerMentor() throws IOException {
        User response = mentorService.findByEmail("wjdalsckd777@naver.com");
        return ResponseEntity.ok().body(response);
    }
}