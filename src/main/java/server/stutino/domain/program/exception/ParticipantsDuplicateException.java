package server.stutino.domain.program.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ParticipantsDuplicateException extends RuntimeException {
    private final String message = "프로그램 참여 내역이 이미 존재합니다.";
    public ParticipantsDuplicateException(String message) {
        super(message);
    }
}