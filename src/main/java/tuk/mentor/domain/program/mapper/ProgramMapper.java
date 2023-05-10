package tuk.mentor.domain.program.mapper;

import org.mapstruct.Mapper;
import tuk.mentor.domain.program.dto.response.ProgramWeekDetailResponse;
import tuk.mentor.domain.program.entity.ProgramWeek;

@Mapper(componentModel = "spring")
public interface ProgramMapper {
    ProgramWeekDetailResponse toProgramWeekDetailDto(ProgramWeek programWeek);
}
