package tuk.mentor.domain.schedule.mapper;

import org.mapstruct.Mapper;
import tuk.mentor.domain.schedule.dto.response.ScheduleListResponse;
import tuk.mentor.domain.schedule.entity.MentorSchedule;

@Mapper(componentModel = "spring")
public interface MentorScheduleMapper {
    ScheduleListResponse toScheduleListDto(MentorSchedule mentorSchedules);
}
