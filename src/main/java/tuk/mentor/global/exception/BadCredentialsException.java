package tuk.mentor.global.exception;

public class BadCredentialsException extends RuntimeException {
    private static final String MESSAGE = "Invalid email or password for mentor.";

    public BadCredentialsException() {
        super(MESSAGE);
    }
}
