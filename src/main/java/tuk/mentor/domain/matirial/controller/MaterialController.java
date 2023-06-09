package tuk.mentor.domain.matirial.controller;

import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tuk.mentor.domain.matirial.service.MaterialService;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    /*
    * 프로그램 별 학습 자료 등록
    * */
    @PostMapping("/program/{programId}")
    public ResponseEntity<Void> registerMaterial(@PathVariable("programId") Long programId,
                                                                 @RequestPart(value = "file", required = false) MultipartFile material,
                                                                 HttpServletRequest servletRequest) throws IOException {
        materialService.registerMaterial(programId, material, servletRequest);
        return ResponseEntity.ok().build();
    }

    /*
    * 학습 자료 다운로드 요청
    * */
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("materialId") Long materialId) throws IOException {
        String fileUrl = materialService.getFilePath(materialId);
        URL url = new URL(fileUrl);
        InputStream inputStream = url.openStream();
        byte[] fileBytes = IOUtils.toByteArray(inputStream);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(fileBytes));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("filename.pdf")
                .build());
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(fileBytes.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}