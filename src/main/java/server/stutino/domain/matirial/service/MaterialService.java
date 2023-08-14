package server.stutino.domain.matirial.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.stutino.domain.matirial.dto.request.MaterialRegisterRequest;
import server.stutino.domain.matirial.dto.response.MaterialDetailResponse;
import server.stutino.domain.matirial.dto.response.MaterialDownloadResponse;
import server.stutino.domain.matirial.dto.response.MaterialListResponse;
import server.stutino.domain.matirial.entity.Material;
import server.stutino.domain.matirial.repository.MaterialRepository;
import server.stutino.domain.program.repository.ProgramRepository;
import server.stutino.util.s3.manager.S3Manager;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialService {

    MaterialRepository materialRepository;
    ProgramRepository programRepository;
    S3Manager s3Manager;

    /*
    *  자료 등록
    * */
    public void registerMaterial(MaterialRegisterRequest request, MultipartFile file) {
        try {
            String filePath = s3Manager.upload(file, "material");

            materialRepository.save(Material.builder()
                    .program(programRepository.findById(request.getProgramId()).orElseThrow(EntityNotFoundException::new))
                    .title(request.getTitle())
                    .detail(request.getDetail())
                    .fileName(file.getOriginalFilename())
                    .filePath(filePath)
                    .build());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     *  자료 목록 조회
     * */
    public List<MaterialListResponse> findAllMaterials(Long programId) {
        return materialRepository.findAllByProgramId(programId).stream().map(material ->
                new MaterialListResponse(
                        material.getId(),
                        material.getTitle(),
                        material.getDetail(),
                        material.getFileName(),
                        material.getFilePath()
                )).toList();
    }

    /*
     *  자료 상세 조회
     * */
    public MaterialDetailResponse findMaterialById(Long materialId) {
        Material material = materialRepository.findAllByMaterialId(materialId);
        return new MaterialDetailResponse(
                materialId,
                material.getTitle(),
                material.getDetail(),
                material.getFileName()
        );
    }

    /*
    * 자료 다운로드
    * */
    public MaterialDownloadResponse downloadMaterial(Long materialId) throws IOException {
        Material material = materialRepository.findById(materialId).orElseThrow(EntityNotFoundException::new);
        System.out.println(material);
        return new MaterialDownloadResponse(
                material.getFileName(),
                material.getFilePath(),
                s3Manager.download(material.getFilePath()));

//        return s3Manager.download(material.getFilePath(), material.getFileName());
    }
}
