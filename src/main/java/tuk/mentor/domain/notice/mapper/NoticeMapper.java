package tuk.mentor.domain.notice.mapper;

import org.mapstruct.Mapper;
import tuk.mentor.domain.notice.dto.response.NoticeListResponse;
import tuk.mentor.domain.notice.entity.ProgramNotice;

@Mapper(componentModel = "spring")
public interface NoticeMapper {
    NoticeListResponse toListResponse(ProgramNotice notices);
}
