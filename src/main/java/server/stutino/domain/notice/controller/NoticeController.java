package server.stutino.domain.notice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.stutino.domain.notice.dto.request.NoticeRegisterRequest;
import server.stutino.domain.notice.dto.response.NoticeListResponse;
import server.stutino.domain.notice.service.NoticeService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NoticeController {

    NoticeService noticeService;

    /*
    * 프로그램 별 공지사항 등록
    * */
    @PostMapping
    public ResponseEntity<Void> registerNotice(@Valid @RequestBody NoticeRegisterRequest noticeRegisterRequest) {
        noticeService.registerNotice(noticeRegisterRequest);
        return ResponseEntity.noContent().build();
    }
    
    /*
    * 프로그램 별 공지사항 목록 조회
    * */
    @GetMapping("/{programId}")
    public ResponseEntity<List<NoticeListResponse>> getNoticeList(@PathVariable("programId") Long programId) {
        List<NoticeListResponse> response = noticeService.getNoticeList(programId);
        return ResponseEntity.ok().body(response);
    }
}