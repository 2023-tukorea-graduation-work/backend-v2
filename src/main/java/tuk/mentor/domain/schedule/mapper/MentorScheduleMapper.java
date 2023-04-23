package tuk.mentor.domain.schedule.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import tuk.mentor.domain.schedule.dto.response.ScheduleListResponse;
import tuk.mentor.domain.schedule.entity.MentorSchedule;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MentorScheduleMapper {
    List<ScheduleListResponse> toScheduleListDto(List<MentorSchedule> mentorSchedules);
}
