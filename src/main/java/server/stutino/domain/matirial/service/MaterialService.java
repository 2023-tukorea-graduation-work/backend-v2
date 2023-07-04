package server.stutino.domain.matirial.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.stutino.domain.matirial.entity.Material;
import server.stutino.domain.matirial.repository.MaterialRepository;
import server.stutino.domain.program.repository.ProgramRepository;
import server.stutino.util.s3.manager.S3Manager;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

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
    public void registerMaterial(Long programId, MultipartFile file) throws IOException {
        String filePath = s3Manager.upload(file, "/material");

        materialRepository.save(Material.builder()
                .program(programRepository.findById(programId).orElseThrow(EntityNotFoundException::new))
                .fileName(file.getOriginalFilename())
                .filePath(filePath)
                .build());
    }

    /*
    * 자료 다운로드
    * */
    public String getFilePath(Long materialId) {
        Material material = materialRepository.findById(materialId).orElseThrow(EntityNotFoundException::new);
        return material.getFilePath();
    }
}
