package server.stutino.domain.member;

public enum Lesson {

    ONLINE("온라인"),
    OFFLINE("오프라인"),
    ONOFFLINE("온/오프라인");
    private final String lesson;
    Lesson(String lesson) {
        this.lesson = lesson;
    }
}
