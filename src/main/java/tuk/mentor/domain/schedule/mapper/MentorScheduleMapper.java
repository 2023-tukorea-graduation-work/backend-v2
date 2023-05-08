package tuk.mentor.domain.schedule.mapper;

import org.mapstruct.Mapper;
import tuk.mentor.domain.schedule.dto.response.ScheduleListResponse;
import tuk.mentor.domain.schedule.entity.MentorSchedule;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MentorScheduleMapper {
    List<ScheduleListResponse> toScheduleListDto(List<MentorSchedule> mentorSchedules);
}
