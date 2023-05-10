package tuk.mentor.domain.mentee.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import tuk.mentor.domain.mentee.dto.request.MenteeRegisterRequest;
import tuk.mentor.domain.mentee.entity.Mentee;

@Mapper(componentModel = "spring")
public interface MenteeMapper {
    @Mapping(target = "imgUrl", ignore = true)
    Mentee toEntityFromRegisterRequest(MenteeRegisterRequest request);
}
