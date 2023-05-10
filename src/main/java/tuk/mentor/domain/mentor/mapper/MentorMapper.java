package tuk.mentor.domain.mentor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tuk.mentor.domain.mentor.dto.request.MentorRegisterRequest;
import tuk.mentor.domain.mentor.entity.Mentor;

@Mapper(componentModel = "spring")
public interface MentorMapper {
    @Mapping(target = "imgUrl", ignore = true)
    Mentor toEntityFromRegisterRequest(MentorRegisterRequest request);
}
