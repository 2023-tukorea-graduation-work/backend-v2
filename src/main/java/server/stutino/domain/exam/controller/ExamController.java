package server.stutino.domain.exam.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.stutino.domain.matirial.service.MaterialService;

import java.io.IOException;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExamController {

    MaterialService materialService;

    /*
    * 프로그램 별 학습 자료 등록
    * */
    @PostMapping("/program/{programId}")
    public ResponseEntity<Void> registerMaterial(@PathVariable("programId") Long programId,
                                                 @RequestPart(value = "file", required = false) MultipartFile material) throws IOException {
        materialService.registerMaterial(programId, material);
        return ResponseEntity.noContent().build();
    }
}