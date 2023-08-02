package server.stutino.domain.member.exception;

import server.stutino.advice.BusinessException;
import server.stutino.advice.ErrorCode;

public class EmailDuplicateException extends BusinessException {

    public EmailDuplicateException() {
        super(ErrorCode.EMAIL_DUPLICATION_ERROR);
    }
}