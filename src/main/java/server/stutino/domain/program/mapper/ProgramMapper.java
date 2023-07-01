package server.stutino.domain.program.mapper;

import org.mapstruct.Mapper;
import server.stutino.domain.program.dto.response.ProgramWeekDetailResponse;
import server.stutino.domain.program.entity.ProgramWeek;

@Mapper(componentModel = "spring")
public interface ProgramMapper {
    ProgramWeekDetailResponse toProgramWeekDetailDto(ProgramWeek programWeek);
}
