package tuk.mentor.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.domain.notice.dto.request.NoticeRegisterRequest;
import tuk.mentor.domain.notice.dto.response.NoticeListResponse;
import tuk.mentor.domain.notice.service.NoticeService;

import java.util.List;


@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<Void> registerNotice(@RequestBody NoticeRegisterRequest noticeRegisterRequest) {
        noticeService.registerNotice(noticeRegisterRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{programId}")
    public ResponseEntity<List<NoticeListResponse>> getNoticeList(@PathVariable("programId") Long programId) {
        List<NoticeListResponse> response = noticeService.getNoticeList(programId);
        return ResponseEntity.ok().body(response);
    }
}