package tuk.mentor.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tuk.mentor.domain.notice.dto.request.NoticeRegisterRequest;
import tuk.mentor.domain.notice.dto.response.NoticeListResponse;
import tuk.mentor.domain.notice.entity.ProgramNotice;
import tuk.mentor.domain.notice.mapper.NoticeMapper;
import tuk.mentor.domain.notice.repository.NoticeRepository;
import tuk.mentor.domain.program.repository.ProgramRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final ProgramRepository programRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;

    /*
    *  공지 등록
    * */
    public void registerNotice(NoticeRegisterRequest request)  {
        noticeRepository.save(ProgramNotice.builder()
                .program(programRepository.findById(request.getProgramId()).orElseThrow(EntityNotFoundException::new))
                .title(request.getTitle())
                .content(request.getContent())
                .build());
    }

    /*
    * 공지 목록 조회
    * */
    public List<NoticeListResponse> getNoticeList(Long programId)  {
        List<ProgramNotice> notices = noticeRepository.findAllByProgramId(programId);
        return notices.stream().map(noticeMapper::toListResponse).toList();
    }
}
