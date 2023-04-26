package tuk.mentor.domain.mentor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tuk.mentor.domain.mentor.dto.request.MentorRegisterRequest;
import tuk.mentor.domain.mentor.dto.response.MentorRegisterResponse;
import tuk.mentor.domain.mentor.service.MentorService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


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
}