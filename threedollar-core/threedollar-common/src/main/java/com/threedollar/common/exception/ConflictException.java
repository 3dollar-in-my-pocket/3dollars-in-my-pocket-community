package com.threedollar.common.exception;

public class ConflictException extends BaseException {

    private static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.E409_CONFLICT;

    public ConflictException(String message) {
        super(message, DEFAULT_ERROR_CODE);
    }

    public ConflictException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, DEFAULT_ERROR_CODE, cause);
    }
}
