package server.stutino.domain.matirial.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.stutino.domain.matirial.dto.request.MaterialRegisterRequest;
import server.stutino.domain.matirial.dto.response.MaterialDetailResponse;
import server.stutino.domain.matirial.dto.response.MaterialDownloadResponse;
import server.stutino.domain.matirial.dto.response.MaterialListResponse;
import server.stutino.domain.matirial.service.MaterialService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialController {

    MaterialService materialService;

    /*
    * 프로그램 별 학습 자료 등록
    * */
    @PostMapping
    public ResponseEntity<Void> registerMaterial(
            @RequestPart(value = "data", required = true) @Valid MaterialRegisterRequest materialRegisterRequest,
            @RequestPart(value = "file", required = true) @Valid MultipartFile file) {
        materialService.registerMaterial(materialRegisterRequest, file);
        return ResponseEntity.ok().build();
    }

    /*
     * 프로그램 별 학습 자료 목록 조회
     * */
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<MaterialListResponse>> findAllMaterials(@PathVariable("programId") Long programId) {
        return ResponseEntity.ok(materialService.findAllMaterials(programId));
    }

    /*
     * 프로그램 별 학습 자료 상세 조회
     * */
    @GetMapping("/{materialId}")
    public ResponseEntity<MaterialDetailResponse> findMaterialById(@PathVariable("materialId") Long materialId) {
        return ResponseEntity.ok(materialService.findMaterialById(materialId));
    }

    /*
    * 학습 자료 다운로드 요청
    * */
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("materialId") Long materialId) throws IOException {

        try{
            MaterialDownloadResponse response = materialService.downloadMaterial(materialId);
            ByteArrayResource resource = new ByteArrayResource(response.getData());
            return ResponseEntity
                    .ok()
                    .contentLength(response.getData().length)
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(response.getFilename(), "utf-8") + "\"")
                    .body(resource.getByteArray());
        }catch (IOException ex){
            return ResponseEntity.badRequest().contentLength(0).body(null);
        }
    }
}