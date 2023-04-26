package tuk.mentor.domain.matirial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tuk.mentor.domain.matirial.entity.ProgramMaterial;
import tuk.mentor.domain.matirial.repository.MaterialRepository;
import tuk.mentor.domain.program.repository.ProgramRepository;
import tuk.mentor.global.s3.manager.S3Manager;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final ProgramRepository programRepository;
    private final S3Manager s3Manager;

    /*
    *  자료 등록
    * */
    public void registerMaterial(Long programId, MultipartFile file, HttpServletRequest servletRequest) throws IOException {
        String filePath = s3Manager.upload(file, s3Manager.getDirName(servletRequest));

        materialRepository.save(ProgramMaterial.builder()
                .program(programRepository.findById(programId).orElseThrow(EntityNotFoundException::new))
                .filePath(filePath)
                .build());
    }

    /*
    * 자료 다운로드
    * */
    public String getFilePath(Long materialId) {
        ProgramMaterial material = materialRepository.findById(materialId).orElseThrow(EntityNotFoundException::new);
        return material.getFilePath();
    }
}
