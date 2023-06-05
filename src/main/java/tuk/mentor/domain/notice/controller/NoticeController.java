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

    /*
    * 프로그램 별 공지사항 등록
    * */
    @PostMapping
    public ResponseEntity<Void> registerNotice(@RequestBody NoticeRegisterRequest noticeRegisterRequest) {
        noticeService.registerNotice(noticeRegisterRequest);
        return ResponseEntity.ok().build();
    }
    
    /*
    * 프로그램 별 공지사항 목록 조회
    * */
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<NoticeListResponse>> getNoticeList(@PathVariable("programId") Long programId) {
        List<NoticeListResponse> response = noticeService.getNoticeList(programId);
        return ResponseEntity.ok().body(response);
    }
}