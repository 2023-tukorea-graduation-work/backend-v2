package server.stutino.domain.program.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.stutino.domain.program.dto.request.ProgramParticipateRequest;
import server.stutino.domain.program.dto.request.ProgramRegisterRequest;
import server.stutino.domain.program.dto.response.ProgramDetailResponse;
import server.stutino.domain.program.dto.response.ProgramListResponse;
import server.stutino.domain.program.service.ProgramService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/program")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProgramController {
    ProgramService programService;

    /*
    * 멘토링 프로그램 등록
    * */
    @PostMapping
    public ResponseEntity<Void> registerProgram(@Valid  @RequestBody ProgramRegisterRequest programRegisterRequest) {
        programService.registerProgram(programRegisterRequest);
        return ResponseEntity.noContent().build();
    }

    /*
    * 멘토링 프로그램 목록 조회
    * */
    @GetMapping
    public ResponseEntity<List<ProgramListResponse>> getProgramList(@RequestParam("keyword") String keyword) {
        List<ProgramListResponse> response = programService.getProgramList(keyword);
        return ResponseEntity.ok().body(response);
    }

    /*
    * 멘토링 프로그램 상세 정보 조회
    * */
    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDetailResponse> getProgramDetail(@PathVariable("programId") Long programId) {
        ProgramDetailResponse response = programService.getProgramDetail(programId);
        return ResponseEntity.ok().body(response);
    }

    /*
    * 멘토링 프로그램 참여 정보 등록
    * */
    @PostMapping("/participate")
    public ResponseEntity<Void> registerParticipation(@Valid @RequestBody ProgramParticipateRequest programParticipateRequest) {
        programService.registerParticipation(programParticipateRequest);
        return ResponseEntity.noContent().build();
    }

    /*
    * 멘토링 프로그램 활동 기록 정보 PDF 파일 생성 요청
    * */
    @GetMapping("/download")
    public ResponseEntity<Void> downloadPdf(@Param("programId") Long programId) {
        programService.downloadPdf(programId);
        return ResponseEntity.noContent().build();
    }
}
