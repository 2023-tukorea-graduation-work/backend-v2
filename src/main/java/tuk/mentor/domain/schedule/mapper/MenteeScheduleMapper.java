package tuk.mentor.domain.schedule.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import tuk.mentor.domain.schedule.dto.response.ScheduleListResponse;
import tuk.mentor.domain.schedule.entity.MenteeSchedule;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenteeScheduleMapper {
    List<ScheduleListResponse> toScheduleListDto(List<MenteeSchedule> menteeSchedules);
}
