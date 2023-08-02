package server.stutino.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,  "서버오류"),
    INPUT_INVALID_ERROR(HttpStatus.BAD_REQUEST,  "잘못된 입력"),

    EMAIL_DUPLICATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "중복된 이메일이 존재합니다. ");

    private final HttpStatus status;
    private final String message;
}