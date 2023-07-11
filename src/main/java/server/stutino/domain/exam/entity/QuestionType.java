package server.stutino.domain.exam.entity;

public enum QuestionType {
    MULTIPLE_CHOICE_QUESTION("객관식"),
    SUBJECT_QUESTION("주관식");
    private final String questionType;
    QuestionType(String questionType) {
        this.questionType = questionType;
    }
}
