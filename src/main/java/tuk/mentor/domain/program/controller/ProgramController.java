package tuk.mentor.domain.program.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.domain.program.dto.request.ProgramParticipateRequest;
import tuk.mentor.domain.program.dto.request.ProgramRegisterRequest;
import tuk.mentor.domain.program.dto.response.ProgramDetailResponse;
import tuk.mentor.domain.program.dto.response.ProgramListResponse;
import tuk.mentor.domain.program.service.ProgramService;

import java.util.List;

@RestController
@RequestMapping("/program")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    @PostMapping
    public ResponseEntity<Void> registerProgram(@RequestBody ProgramRegisterRequest programRegisterRequest) {
        programService.registerProgram(programRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProgramListResponse>> getProgramList(@RequestParam("keyword") String keyword) {
        List<ProgramListResponse> response = programService.getProgramList(keyword);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDetailResponse> getProgramDetail(@PathVariable("programId") Long programId) {
        ProgramDetailResponse response = programService.getProgramDetail(programId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/participation")
    public ResponseEntity<Void> registerParticipation(@RequestBody ProgramParticipateRequest programParticipateRequest) {
        programService.registerParticipation(programParticipateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/download")
    public ResponseEntity<Void> downloadPdf(@Param("programId") Long programId) {
        programService.downloadPdf(programId);
        return ResponseEntity.ok().build();
    }
}
