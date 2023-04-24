package tuk.mentor.domain.matirial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tuk.mentor.domain.notice.dto.request.NoticeRegisterRequest;

import java.io.File;

@Service
@RequiredArgsConstructor
public class MaterialService {


    /*
    *  자료 등록
    * */
    public void registerMaterial(NoticeRegisterRequest request)  {

    }

    /*
    * 자료 다운로드
    * */
    public File downloadMaterial(Long materialId) {

    }
}
