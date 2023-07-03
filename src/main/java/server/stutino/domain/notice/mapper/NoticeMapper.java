package server.stutino.domain.notice.mapper;

import org.mapstruct.Mapper;
import server.stutino.domain.notice.dto.response.NoticeListResponse;
import server.stutino.domain.notice.entity.Notice;

@Mapper(componentModel = "spring")
public interface NoticeMapper {
    NoticeListResponse toNoticeListDto(Notice notices);
}
