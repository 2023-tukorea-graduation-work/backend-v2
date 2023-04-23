package tuk.mentor.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tuk.mentor.global.advice.ErrorResponse;

@Aspect
@Slf4j
@Component
public class ServiceExceptionHandler {
    @Pointcut("execution(* tuk.mentor..*Service*.*(..))")
    public void allService() {}

    @AfterThrowing(value = "execution(* tuk.mentor..*Service*.*(..))", throwing = "ex")
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ex.getMessage()));
    }
}
