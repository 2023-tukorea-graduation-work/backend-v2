package tuk.mentor.domain.user.mentor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import tuk.mentor.domain.user.mentor.dto.request.MentorRegisterRequest;
import tuk.mentor.domain.user.mentor.entity.Mentor;

@Mapper(componentModel = "spring")
public interface MentorMapper {
    @Mappings({
            @Mapping(target = "imgUrl", ignore = true),
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Mentor toEntityFromRegisterRequest(MentorRegisterRequest request);
}
