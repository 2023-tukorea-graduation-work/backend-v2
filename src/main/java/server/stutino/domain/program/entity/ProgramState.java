package server.stutino.domain.program.entity;

public enum ProgramState {

    RECRUIT("모집중"),
    OPEN("진행중"),
    CLOSE("완료");

    private final String programState;

    ProgramState(String programState) {
        this.programState = programState;
    }
}
