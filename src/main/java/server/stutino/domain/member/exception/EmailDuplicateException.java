package server.stutino.domain.member.exception;

import lombok.Getter;

@Getter
public class EmailDuplicateException extends RuntimeException{
    private String message;

    public EmailDuplicateException(String message) {
        super(message);
        this.message = message;
    }
}
