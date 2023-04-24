package tuk.mentor.domain.notice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import tuk.mentor.domain.notice.dto.response.NoticeListResponse;
import tuk.mentor.domain.notice.entity.ProgramNotice;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NoticeMapper {
    List<NoticeListResponse> toListResponse(List<ProgramNotice> notices);
}
