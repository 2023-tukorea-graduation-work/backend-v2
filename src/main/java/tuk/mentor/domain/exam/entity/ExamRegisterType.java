package tuk.mentor.domain.exam.entity;

public enum ExamRegisterType {
    TEMP("임시저장"),
    COMPLETE("등록");

    private final String examRegisterType;

    ExamRegisterType(String examRegisterType) {
        this.examRegisterType = examRegisterType;
    }
}
