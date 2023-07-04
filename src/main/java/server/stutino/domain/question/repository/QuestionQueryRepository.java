package server.stutino.domain.question.repository;

import server.stutino.domain.question.dto.response.QuestionListResponse;

import java.util.List;

public interface QuestionQueryRepository {
    List<QuestionListResponse> getAllQuestionByProgramId(Long programId);
}
