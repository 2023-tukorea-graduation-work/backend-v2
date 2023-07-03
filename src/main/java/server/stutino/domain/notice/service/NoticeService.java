package server.stutino.domain.notice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import server.stutino.domain.notice.dto.request.NoticeRegisterRequest;
import server.stutino.domain.notice.dto.response.NoticeListResponse;
import server.stutino.domain.notice.entity.Notice;
import server.stutino.domain.notice.mapper.NoticeMapper;
import server.stutino.domain.notice.repository.NoticeRepository;
import server.stutino.domain.program.repository.ProgramRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NoticeService {

    ProgramRepository programRepository;
    NoticeRepository noticeRepository;
    NoticeMapper noticeMapper;

    /*
    *  공지 등록
    * */
    public void registerNotice(NoticeRegisterRequest request)  {
        noticeRepository.save(Notice.builder()
                .program(programRepository.findById(request.getProgramId()).orElseThrow(EntityNotFoundException::new))
                .title(request.getTitle())
                .content(request.getContent())
                .build());
    }

    /*
    * 공지 목록 조회
    * */
    public List<NoticeListResponse> getNoticeList(Long programId)  {
        List<Notice> notices = noticeRepository.findAllByProgramId(programId);
        return notices.stream().map(noticeMapper::toNoticeListDto).toList();
    }
}
