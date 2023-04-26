package tuk.mentor.domain.mentee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tuk.mentor.domain.mentee.dto.request.MenteeRegisterRequest;
import tuk.mentor.domain.mentee.dto.response.MenteeRegisterResponse;
import tuk.mentor.domain.mentee.service.MenteeService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
@RequestMapping("/mentee")
@RequiredArgsConstructor
public class MenteeController {

    private final MenteeService menteeService;

    @PostMapping
    public ResponseEntity<MenteeRegisterResponse> registerMentee(@ModelAttribute MenteeRegisterRequest menteeRegisterRequest,
                                                                 @RequestPart(value = "file", required = false) MultipartFile image,
                                                                 HttpServletRequest servletRequest) throws IOException {
        MenteeRegisterResponse response = menteeService.registerMentee(menteeRegisterRequest, image, servletRequest);
        return ResponseEntity.ok().body(response);
    }
}