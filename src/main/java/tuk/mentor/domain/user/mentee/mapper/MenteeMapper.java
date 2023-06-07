package tuk.mentor.domain.user.mentee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import tuk.mentor.domain.user.mentee.dto.request.MenteeRegisterRequest;
import tuk.mentor.domain.user.mentee.entity.Mentee;

@Mapper(componentModel = "spring")
public interface MenteeMapper {
    @Mappings({
            @Mapping(target = "imgUrl", ignore = true),
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Mentee toEntityFromRegisterRequest(MenteeRegisterRequest request);
}
