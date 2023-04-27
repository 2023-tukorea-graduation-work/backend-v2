package tuk.mentor.domain.question.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import tuk.mentor.domain.question.dto.response.QuestionListResponse;
import tuk.mentor.domain.question.entity.Question;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionMapper {
    List<QuestionListResponse> toQuestionListDto(List<Question> questions);
}
