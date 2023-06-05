package tuk.mentor.domain.user.mentee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tuk.mentor.domain.user.mentee.dto.request.MenteeRegisterRequest;
import tuk.mentor.domain.user.mentee.service.MenteeService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
@RequestMapping("/mentee")
@RequiredArgsConstructor
public class MenteeController {

    private final MenteeService menteeService;

    /*
    * 멘티 등록
    * */
    @PostMapping
    public ResponseEntity<Void> registerMentee(@ModelAttribute MenteeRegisterRequest menteeRegisterRequest,
                                                                 @RequestPart(value = "file", required = false) MultipartFile image,
                                                                 HttpServletRequest servletRequest) throws IOException {
        menteeService.registerMentee(menteeRegisterRequest, image, servletRequest);
        return ResponseEntity.ok().build();
    }
}