package tuk.mentor.domain.user.mentor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tuk.mentor.domain.user.mentor.dto.request.MentorRegisterRequest;
import tuk.mentor.domain.user.mentor.service.MentorService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
@RequestMapping("/mentor")
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;

    /*
    * 멘토 등록
    * */
    @PostMapping
    public ResponseEntity<Void> registerMentor(@ModelAttribute MentorRegisterRequest mentorRegisterRequest,
                                                                 @RequestPart(value = "file", required = false) MultipartFile image,
                                                                HttpServletRequest servletRequest) throws IOException {
        mentorService.registerMentor(mentorRegisterRequest, image, servletRequest);
        return ResponseEntity.ok().build();
    }
}