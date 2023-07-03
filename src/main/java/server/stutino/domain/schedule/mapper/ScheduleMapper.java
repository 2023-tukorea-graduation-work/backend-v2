package server.stutino.domain.schedule.mapper;

import org.mapstruct.Mapper;
import server.stutino.domain.schedule.dto.response.ScheduleListResponse;
import server.stutino.domain.schedule.entity.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    ScheduleListResponse toScheduleListDto(Schedule schedule);
}
