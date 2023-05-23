package tuk.mentor.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tuk.mentor.global.advice.ErrorResponse;

@RequiredArgsConstructor
@RequestMapping("/forbidden")
public class ForbiddenController {

    @GetMapping
    public ResponseEntity<ErrorResponse> accessDenied(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorResponse(e.getMessage()));
    }
}
