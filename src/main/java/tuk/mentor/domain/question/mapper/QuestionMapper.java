package tuk.mentor.domain.question.mapper;


import org.mapstruct.Mapper;
import tuk.mentor.domain.question.dto.response.QuestionListResponse;
import tuk.mentor.domain.question.entity.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionListResponse toQuestionListDto(Question questions);
}
