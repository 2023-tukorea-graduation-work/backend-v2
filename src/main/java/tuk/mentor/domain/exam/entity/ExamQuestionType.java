package tuk.mentor.domain.exam.entity;

public enum ExamQuestionType {
    MULTI_CHOICE_QUESTION("객관식"),
    ESSAY_QUESTION("주관식");

    private final String examQuestionType;

    ExamQuestionType(String examQuestionType) {
        this.examQuestionType = examQuestionType;
    }
}
