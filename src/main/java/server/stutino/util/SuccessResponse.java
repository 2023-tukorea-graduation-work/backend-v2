package server.stutino.util;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Slf4j
public class SuccessResponse {
    private int status;
    private String message;
    private SuccessResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public static SuccessResponse of(int status, String message) {
        return new SuccessResponse(status, message);
    }

    public static SuccessResponse of(HttpStatus httpStatus) {
        return new SuccessResponse(httpStatus.value(), httpStatus.getReasonPhrase());
    }
    public static SuccessResponse of(HttpStatus httpStatus, String message) {
        return new SuccessResponse(httpStatus.value(), message);
    }
}