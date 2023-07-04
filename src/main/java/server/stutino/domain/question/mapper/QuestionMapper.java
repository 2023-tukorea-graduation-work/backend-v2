package server.stutino.domain.question.mapper;


import org.mapstruct.Mapper;
import server.stutino.domain.question.dto.response.QuestionListResponse;
import server.stutino.domain.question.entity.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionListResponse toQuestionListDto(Question questions);
}
