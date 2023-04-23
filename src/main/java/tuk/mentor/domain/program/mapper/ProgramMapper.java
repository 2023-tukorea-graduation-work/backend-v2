package tuk.mentor.domain.program.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import tuk.mentor.domain.program.dto.response.ProgramWeekDetailResponse;
import tuk.mentor.domain.program.entity.ProgramWeek;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProgramMapper {
    List<ProgramWeekDetailResponse> toProgramWeekDetailDto(List<ProgramWeek> programWeek);
}
