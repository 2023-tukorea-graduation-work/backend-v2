package tuk.mentor.domain.mentee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import tuk.mentor.domain.mentee.dto.request.MenteeRegisterRequest;
import tuk.mentor.domain.mentee.entity.Mentee;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenteeMapper {
    Mentee toEntityFromRegisterRequest(MenteeRegisterRequest request);
}
