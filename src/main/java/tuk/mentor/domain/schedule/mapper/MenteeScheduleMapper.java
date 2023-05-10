package tuk.mentor.domain.schedule.mapper;

import org.mapstruct.Mapper;
import tuk.mentor.domain.schedule.dto.response.ScheduleListResponse;
import tuk.mentor.domain.schedule.entity.MenteeSchedule;

@Mapper(componentModel = "spring")
public interface MenteeScheduleMapper {
    ScheduleListResponse toScheduleListDto(MenteeSchedule menteeSchedules);
}
