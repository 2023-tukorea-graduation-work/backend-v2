package tuk.mentor.domain.matirial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.domain.matirial.service.MaterialService;
import tuk.mentor.domain.notice.dto.request.NoticeRegisterRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping
    public ResponseEntity<Void> registerNotice(@RequestBody NoticeRegisterRequest noticeRegisterRequest) {
        materialService.registerMaterial(noticeRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("materialId") Long materialId) throws IOException {
        // Check if the requested file exists

        File file = materialService.downloadMaterial(materialId);
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getName());

        // Create response entity with file input stream
        InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
        ResponseEntity<InputStreamResource> responseEntity = new ResponseEntity<>(isr, headers, HttpStatus.OK);
        return responseEntity;
    }
}