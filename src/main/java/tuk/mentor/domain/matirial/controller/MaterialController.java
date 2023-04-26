package tuk.mentor.domain.matirial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tuk.mentor.domain.matirial.service.MaterialService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping("/{programId}")
    public ResponseEntity<Void> registerMentor(@PathVariable("programId") Long programId,
                                                                 @RequestPart(value = "file", required = false) MultipartFile material,
                                                                 HttpServletRequest servletRequest) throws IOException {
        materialService.registerMaterial(programId, material, servletRequest);
        return ResponseEntity.ok().build();
    }

    /*@GetMapping
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

        // Create response ent ity with file input stream
        InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
        ResponseEntity<InputStreamResource> responseEntity = new ResponseEntity<>(isr, headers, HttpStatus.OK);
        return responseEntity;
    }*/
}